package com.fasthopp.taskservice.dto;

import com.fasthopp.taskservice.entity.Board;
import com.fasthopp.taskservice.entity.BoardColumn;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Long id;
    private String title;
    private String description;
    private Integer maxMembers;
    private Timestamp createdAt;
    private String colour;
    private String priority;
    private Long boardColumnId;
    private Long boardId;
    private List<AssigneeDto> assignee;
    private List<CommentDto> comments;

}
