package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritePlace {
    private String id;
    private String userId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String iconType;
}
