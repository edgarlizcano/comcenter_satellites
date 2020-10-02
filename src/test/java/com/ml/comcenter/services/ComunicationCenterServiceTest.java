package com.ml.comcenter.services;

import com.ml.comcenter.dtos.PositionDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author elizcano
 */
class ComunicationCenterServiceTest {

    /**
     * Test para evaluar la generación de la ubicación del emisor segun
     * la ubicación de los satélites y la distancia con el emisor
     */
    @Test
    void getLocation() {
        ComunicationCenterService centerService = new ComunicationCenterService();
        PositionDto p1 = new PositionDto(-500, -200, 100);
        PositionDto p2 = new PositionDto(100, -100, 115.5);
        PositionDto p3 = new PositionDto(500, 100, 142.7);
        PositionDto pos = centerService.getLocation(p1, p2, p3);
        assertEquals(-487.2859125, pos.getX());
        assertEquals(1557.014225, pos.getY());
    }

    /**
     * Test para evaluar la salida del mensaje descifrado
     */
    @Test
    void testGetMessage() {
        String message = null;
        ComunicationCenterService centerService = new ComunicationCenterService();
        List<String> messages1 = Arrays.asList("","es", "", "", "secreto");
        List<String> messages2 = Arrays.asList("este","es", "", "mensaje", "");
        List<String> messages3 = Arrays.asList("este","", "un", "", "secreto");
        message = centerService.getMessage(Arrays.asList(messages1, messages2, messages3));
        assertEquals("este es un mensaje secreto", message);
    }

    /**
     * Test para evaluar la función cuando no se tiene suficiente información
     * para descifrar el mensaje
     */
    @Test
    void testGetMessageNull() {
        String message = null;
        ComunicationCenterService centerService = new ComunicationCenterService();
        List<String> messages1 = Arrays.asList("","es", "", "", "secreto");
        List<String> messages2 = Arrays.asList("este","es", "", "mensaje", "");
        List<String> messages3 = Arrays.asList("este","", "", "", "secreto");
        message = centerService.getMessage(Arrays.asList(messages1, messages2, messages3));
        assertNull(message);
    }

}