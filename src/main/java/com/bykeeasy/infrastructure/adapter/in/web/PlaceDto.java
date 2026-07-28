package com.bykeeasy.infrastructure.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
