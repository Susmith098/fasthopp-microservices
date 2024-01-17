package com.fasthopp.userservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileGetDto {

    private Integer userId;
    private String name;
    private String email;
    private String role;
    private String companyName;
    private String address;
    private String designation;
    private LocalDateTime joinedAt;
    private boolean isDeleted;
    private boolean blocked;
}
