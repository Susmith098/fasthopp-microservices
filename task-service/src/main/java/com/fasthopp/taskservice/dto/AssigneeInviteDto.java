package com.fasthopp.taskservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeInviteDto {
    private List<String> userEmails;

    private Long cardId;
}
