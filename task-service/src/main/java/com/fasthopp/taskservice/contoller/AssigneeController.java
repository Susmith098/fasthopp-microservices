package com.fasthopp.taskservice.contoller;

import com.fasthopp.taskservice.dto.AssigneeDto;
import com.fasthopp.taskservice.dto.AssigneeInviteDto;
import com.fasthopp.taskservice.dto.AssigneeResponseDto;
import com.fasthopp.taskservice.service.AssigneeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("task/assignee")
@Tag(name = "Assignee Endpoints")
public class AssigneeController {

    private final AssigneeService assigneeService;

    public AssigneeController(AssigneeService assigneeService) {
        this.assigneeService = assigneeService;
    }

    @Operation(
            description = "Post endpoint for adding/assigning a new user to a card.",
            summary = "Adding/assigning a new user to a card.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/invite")
    public AssigneeResponseDto addAssignee(@RequestBody AssigneeInviteDto assigneeInviteDto) {
        return assigneeService.addAssignee(assigneeInviteDto);
    }

    private AssigneeInviteDto convertResponseDtoToInviteDto(AssigneeResponseDto responseDto) {
        AssigneeInviteDto inviteDto = new AssigneeInviteDto();
        inviteDto.setUserEmails(Collections.singletonList(responseDto.getUserEmail()));
        inviteDto.setCardId(responseDto.getCardId());
        return inviteDto;
    }

    @Operation(
            description = "Delete endpoint for deleting/removing a assignee from a card.",
            summary = "Deleting/removing a assignee from a card.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping("/{assigneeId}")
    public void deleteAssignee(@PathVariable Long assigneeId) {
        assigneeService.deleteAssignee(assigneeId);
    }

    @Operation(
            description = "Get endpoint for get all assignees in a card.",
            summary = "Get all assignees in a card.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/card/{cardId}")
    public List<AssigneeResponseDto> getAllAssigneesForCard(@PathVariable Long cardId) {
        return assigneeService.getAllAssigneesForCard(cardId);
    }
}
