package com.ml.comcenter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInDto {
    private String name;
    private double distance;
    private List<String> message;
}
