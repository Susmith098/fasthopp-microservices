package com.fasthopp.taskservice.service;

import com.fasthopp.taskservice.dto.AssigneeDto;
import com.fasthopp.taskservice.dto.AssigneeInviteDto;
import com.fasthopp.taskservice.dto.AssigneeResponseDto;

import java.util.List;

public interface AssigneeService {

//    List<AssigneeInviteDto> addAssignees(Long cardId, List<String> userEmail);
    AssigneeResponseDto addAssignee(AssigneeInviteDto assigneeInviteDto);

    void deleteAssignee(Long assigneeId);
    List<AssigneeResponseDto> getAllAssigneesForCard(Long cardId);
}
