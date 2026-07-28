package com.bykeeasy.infrastructure.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecentDestinationDto {
    private String id;
    private String title;
    private String subtitle;
    private String type;
    private double latitude;
    private double longitude;
}