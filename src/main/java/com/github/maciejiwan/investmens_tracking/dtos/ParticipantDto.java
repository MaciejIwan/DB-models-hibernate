package com.github.maciejiwan.investmens_tracking.dtos;

import lombok.Data;

@Data
public class ParticipantDto {
    private String name;
    private String email;

    public ParticipantDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
