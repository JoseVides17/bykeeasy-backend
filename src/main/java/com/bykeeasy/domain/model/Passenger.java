package com.bykeeasy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private int qualification;
    private String profileImageUrl;

    public Passenger(String id, String fullName, String phone, String email, String password, int i) {
    }
}
