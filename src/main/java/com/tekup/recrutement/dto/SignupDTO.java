package com.tekup.recrutement.dto;

import com.tekup.recrutement.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupDTO {

    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
