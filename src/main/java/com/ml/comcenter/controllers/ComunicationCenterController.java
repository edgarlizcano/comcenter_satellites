package com.ml.comcenter.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.comcenter.dtos.MessageInDto;
import com.ml.comcenter.dtos.MessageOutDto;
import com.ml.comcenter.dtos.SatelliteInDto;
import com.ml.comcenter.services.ComunicationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author elizcano
 */
@RestController
@RequestMapping("comcenter")
public class ComunicationCenterController {

    @Autowired
    private ComunicationCenterService centerService;

    /**
     * @return
     */
    @GetMapping("info")
    public String info() {
        return "ComCenter V 1.0.0";
    }

    @PostMapping("topsecret")
    public ResponseEntity<MessageOutDto> topSecret(@RequestBody SatelliteInDto satelliteInDto){
        return centerService.topSecret(satelliteInDto);
    }

    @PostMapping("topsecret_split/{satellite_name}")
    public ResponseEntity topSecret(@PathVariable("satellite_name") String satelliteName, @RequestBody MessageInDto messageInDto){
        messageInDto.setName(satelliteName);
        return centerService.topSecretSplit(messageInDto);
    }

    @GetMapping("topsecret_split")
    public ResponseEntity topSecretGet() {
        return centerService.topSecretGet();
    }
}
