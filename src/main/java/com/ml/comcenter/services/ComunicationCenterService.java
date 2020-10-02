package com.ml.comcenter.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.comcenter.dtos.MessageInDto;
import com.ml.comcenter.dtos.MessageOutDto;
import com.ml.comcenter.dtos.PositionDto;
import com.ml.comcenter.dtos.SatelliteInDto;
import com.ml.comcenter.entities.MessageEntity;
import com.ml.comcenter.entities.SatelliteEntity;
import com.ml.comcenter.repositories.MessageRepository;
import com.ml.comcenter.repositories.SatelliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author elizcano
 */
@Service
public class ComunicationCenterService {

    private Logger LOGGER = LoggerFactory.getLogger(ComunicationCenterService.class);

    @Autowired
    private SatelliteRepository satelliteRepository;
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Método que recibe los datos enviados de los satélites para proceder a descifrarlos e
     * intentar obtener la ubicación del emisor
     *
     * @param dataIn
     * @return
     */
    public ResponseEntity<MessageOutDto> topSecret(SatelliteInDto dataIn){
        MessageOutDto out = new MessageOutDto();
        List<List<String>> listMsgs = dataIn.getSatellites()
                .stream()
                .map(MessageInDto::getMessage)
                .collect(Collectors.toList());
        String messageDecoded = getMessage(listMsgs);
        if(dataIn.getSatellites().size() < 3){
            LOGGER.warn("Número de satelites insuficientes para calcular ubicación del emisor");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(messageDecoded == null){
            LOGGER.warn("No hay suficiente información para decodificar el mensaje");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        PositionDto p1 = getSatellitePosition(dataIn.getSatellites().get(0).getName(), dataIn.getSatellites().get(0).getDistance());
        PositionDto p2 = getSatellitePosition(dataIn.getSatellites().get(1).getName(), dataIn.getSatellites().get(1).getDistance());
        PositionDto p3 = getSatellitePosition(dataIn.getSatellites().get(2).getName(), dataIn.getSatellites().get(2).getDistance());
        if(p1 == null || p2 == null || p3 == null){
            LOGGER.warn("No se pudo determinar la ubicación del emisor");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        out.setPosition(getLocation(p1, p2, p3));
        out.setMessage(messageDecoded);
        return ResponseEntity.status(HttpStatus.OK).body(out);
    }

    /**
     * Función para calcular posición del emisor segun los puntos del satélite y distancia con
     * respecto al emisor
     * Se utiliza el método de Trilateración para obtener la posición del emisor en un plano de dos dimensiones
     *
     * Algoritmo de: Radhika S. Grover
     * Libro: "Programming with Java: A Multimedia Approach"
     *
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public PositionDto getLocation(PositionDto p1, PositionDto p2, PositionDto p3){
        double x, y;
        double xP1Sq = Math.pow(p1.getX(), 2), xP2Sq = Math.pow(p2.getX(), 2), xP3Sq = Math.pow(p3.getX(), 2);
        double yP1Sq = Math.pow(p1.getY(), 2), yP2Sq = Math.pow(p2.getY(), 2), yP3Sq = Math.pow(p3.getY(), 2);
        double r1Sq = Math.pow(p1.getDistance(), 2), r2Sq = Math.pow(p2.getDistance(), 2), r3Sq = Math.pow(p3.getDistance(), 2);
        double numY = (p2.getX() - p1.getX()) * (xP3Sq + yP3Sq - r3Sq) + (p1.getX() - p3.getX()) * (xP2Sq + yP2Sq - r2Sq) + (p3.getX() - p2.getX()) * (xP1Sq + yP1Sq - r1Sq);
        double denomY = 2 * (p3.getY() * (p2.getX() - p1.getX()) + p2.getY() * (p1.getX() - p3.getX()) + p1.getY() * (p3.getX() - p2.getX()));
        y = numY / denomY;
        double numX= r2Sq - r1Sq + xP1Sq - xP2Sq +yP1Sq - yP2Sq - 2 *(p1.getY() - p2.getY()) * y;
        double denomX = 2 * (p1.getX() - p2.getX());
        x = numX / denomX;
        LOGGER.info("Posición del emisor encontrada en ({},{})", x, y);
        return new PositionDto(x, y, 0);
    }

    /**
     * Función para descifrar mensaje desde los diferentes datos recibidos
     * retorna el mensaje
     *
     * @param messagesList
     * @return
     */
    public String getMessage(List<List<String>> messagesList){
        List<String> message = new ArrayList<>();
        AtomicInteger numMaxItems = new AtomicInteger();
        messagesList.forEach(item -> numMaxItems.set(Math.max(item.size(), numMaxItems.get())));
        for (int i = 0; i < numMaxItems.get(); i++) {
            int finalI = i;
            AtomicReference<String> msg = new AtomicReference<>(null);
            messagesList.forEach(item -> {
                if(finalI < item.size()){
                    if(!item.get(finalI).isEmpty())
                        msg.set(item.get(finalI));
                }
            });
            if(msg.get() == null) return null;
            message.add(msg.get());
        }
        if(message.size() != numMaxItems.get()){
            return null;
        }
        return String.join(" ", message);
    }

    /**
     * Función para obtener posición de un satélite desde la base de datos
     * Recibe el nombre del satélite
     *
     * @param satelliteName
     * @return
     */
    private PositionDto getSatellitePosition(String satelliteName, double distance){
        Optional<SatelliteEntity> optSat = satelliteRepository.findBySatelliteName(satelliteName.toUpperCase());
        return optSat.map(satelliteEntity -> new PositionDto(satelliteEntity.getPositionX(), satelliteEntity.getPositionY(), distance))
                .orElse(null);
    }

    /**
     * Método que recibe la información parcial de un satelite
     * para almacenarla en la base de datos
     *
     * @param messageInDto
     * @return
     */
    public ResponseEntity topSecretSplit(MessageInDto messageInDto) {
        Optional<SatelliteEntity> optSatellite = satelliteRepository.findBySatelliteName(messageInDto.getName());
        if(!optSatellite.isPresent()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        MessageEntity message = new MessageEntity();
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        message.setSatellite(optSatellite.get());
        message.setMessageDistance(messageInDto.getDistance());
        String mess = String.join(",", messageInDto.getMessage());
        message.setMessageData(mess);
        messageRepository.save(message);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Método para obtener los ultimos mensajes guardados de los satelites registrados
     * para intentar obtener el mensaje decodificado
     *
     * @return
     */
    public ResponseEntity topSecretGet() {
        List<SatelliteEntity> satelliteList = satelliteRepository.findAll();
        List<List<String>> listaMess = new ArrayList<>();
        MessageOutDto out = new MessageOutDto();
        List<PositionDto> pointsList = satelliteList.stream().map(item -> {
            Optional<MessageEntity> optMess = messageRepository.findBySatelliteOrderByMessageDateDesc(item).stream().findFirst();
            PositionDto p = null;
            if(optMess.isPresent()){
                listaMess.add(Arrays.asList(optMess.get().getMessageData().split(",")));
                p = new PositionDto(item.getPositionX(), item.getPositionY(), optMess.get().getMessageDistance());
            }
            return p;
        }).collect(Collectors.toList());
        boolean haveNulls = pointsList.stream().anyMatch(Objects::isNull);
        String mess = getMessage(listaMess);
        if(mess == null || haveNulls){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay suficiente información");
        }
        out.setPosition(getLocation(pointsList.get(0), pointsList.get(1), pointsList.get(2)));
        out.setMessage(mess);
        return ResponseEntity.status(HttpStatus.OK).body(out);
    }
}
