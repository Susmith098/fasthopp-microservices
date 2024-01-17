package com.fasthopp.taskservice.meeting.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Column(nullable = false)
    private String description;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "organizer_id", nullable = false)
    private Long organizerId;

    @Column(name = "starting_time")
    private Timestamp startingTime;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;


}

