package com.fasthopp.taskservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long cardId;
    private Long userId;
    private String username;
    private String comment;
    private LocalDateTime createdAt;

}
