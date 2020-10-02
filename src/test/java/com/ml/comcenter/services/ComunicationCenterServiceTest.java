package com.ml.comcenter.services;

import com.ml.comcenter.dtos.PositionDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComunicationCenterServiceTest {

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

    @Test
    void getMessage() {
        ComunicationCenterService centerService = new ComunicationCenterService();
        List<String> messages1 = Arrays.asList("","es", "", "", "secreto");
        List<String> messages2 = Arrays.asList("este","es", "", "mensaje", "");
        List<String> messages3 = Arrays.asList("este","", "un", "", "secreto");
        String message = centerService.getMessage(Arrays.asList(messages1, messages2, messages3));
        assertEquals("este es un mensaje secreto", message);
    }
}