package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecentDestination {
    private String id;
    private String title;
    private String subtitle;
    private String type;
    private double latitude;
    private double longitude;
}