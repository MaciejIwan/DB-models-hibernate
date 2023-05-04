package com.github.maciejiwan.investmens_tracking.dtos;

import com.github.maciejiwan.investmens_tracking.entities.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDto {
    private final String name;
    private final String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDto(UserModel user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
