package com.fasthopp.userservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer userId;
    private String name;
    private String email;
    private String role;
    private String companyName;

}
