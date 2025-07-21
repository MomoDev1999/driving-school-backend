package com.momodev.drivingschool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentWithReadStatus {
    private Integer id;
    private String title;
    private String paragraph;
    private boolean read;
}