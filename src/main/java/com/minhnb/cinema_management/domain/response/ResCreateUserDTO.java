package com.minhnb.cinema_management.domain.response;

import java.time.Instant;
import java.time.LocalDate;

import com.minhnb.cinema_management.util.constant.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private String phone;
    private LocalDate dateOfBirth;
    private Instant createdAt;
}
