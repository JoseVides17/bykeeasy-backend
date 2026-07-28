package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String id;
    private String licensePlate;
    private String type; // MOTO, etc.
    private String model;
    private String color;
    private String brand;
    private String imageUrl;
}
