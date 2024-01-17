package com.fasthopp.taskservice.dto;

import com.fasthopp.taskservice.entity.Card;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardColumnDto {

    private Long id;
    private String name;
    private Integer columnOrder;
    private Long boardId;
}
