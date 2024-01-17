package com.fasthopp.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterEvent {
    private String username;
    private String email;
    private String companyName;
    private LocalDateTime createdAt;
}
