package com.fasthopp.taskservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeResponseDto {

    private Long id;

    private String userEmail;

    private Long cardId;

}

