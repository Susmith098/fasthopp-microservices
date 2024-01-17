package com.fasthopp.taskservice.meeting.dto;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {

    private Long id;
    private String roomId;
    private String description;
    private String companyName;
    private Long organizerId;
    private Timestamp startingTime;
    private Timestamp expirationTime;

}
