package com.fasthopp.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
