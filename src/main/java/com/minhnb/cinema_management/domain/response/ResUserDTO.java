package com.minhnb.cinema_management.domain.response;

import java.time.LocalDate;

import com.minhnb.cinema_management.domain.Role;
import com.minhnb.cinema_management.util.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
    private Long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private LocalDate dateOfBirth;
    private String phone;
    private Role role;
    private boolean enabled;
}
