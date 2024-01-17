package com.fasthopp.taskservice.dto;

import com.fasthopp.taskservice.entity.BoardColumn;
import com.fasthopp.taskservice.entity.Card;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private Long userId;
    private String companyName;
    private String name;
    private String description;
    private LocalDateTime createdDate;
}
