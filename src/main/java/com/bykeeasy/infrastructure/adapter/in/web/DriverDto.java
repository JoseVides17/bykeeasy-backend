package com.bykeeasy.infrastructure.adapter.in.web;

import lombok.Data;

@Data
public class DriverDto {
    private String id;
    private String name;
    private String phone;
    private int qualification;
    private String past;
    private String status;
}
