package com.fasthopp.taskservice.dto;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AssigneeDto {

    private String userEmail;

}
