package com.fasthopp.taskservice.meeting.controller;

import com.fasthopp.taskservice.meeting.dto.MeetingDto;
import com.fasthopp.taskservice.meeting.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/meeting")
@Tag(name = "Meeting Endpoints")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Operation(
            description = "Get endpoint for get meeting by room ID.",
            summary = "Get meeting by room ID.",
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
    @GetMapping("/room/{roomId}")
    public MeetingDto getMeetingByRoomId(@PathVariable String roomId) {
        return meetingService.getMeetingByRoomId(roomId);
    }

    @Operation(
            description = "Get endpoint for get all meetings.",
            summary = "Get all meetings.",
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
    @GetMapping("/meetings")
    public List<MeetingDto> getMeetingsByCompanyName(@RequestParam String companyName) {
        return meetingService.getMeetingsByCompanyName(companyName);
    }

    @Operation(
            description = "Post endpoint for create new meeting.",
            summary = "Create a new meeting.",
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
    @PostMapping("/create")
    public MeetingDto createMeeting(@RequestBody MeetingDto meetingDto) {
        System.out.println(meetingDto + "from controller");
        return meetingService.createMeeting(meetingDto);
    }

    @Operation(
            description = "Delete endpoint for delete an existing meeting.",
            summary = "Delete an existing meeting.",
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
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteMeetingById(@PathVariable Long id) {
        boolean deleted = meetingService.deleteMeetingById(id);

        if (deleted) {
            return ResponseEntity.ok("Meeting deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting not found");
        }
    }
}
