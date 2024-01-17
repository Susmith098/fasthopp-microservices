package com.fasthopp.notificationservice.controller;

import com.fasthopp.notificationservice.entity.UserRegistrationMessage;
import com.fasthopp.notificationservice.repository.UserRegistrationMessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification/messages")
@Tag(name = "Notification Endpoints")
public class UserRegistrationMessageController {

    private final UserRegistrationMessageRepository registrationMessageRepository;

    @Autowired
    public UserRegistrationMessageController(UserRegistrationMessageRepository registrationMessageRepository) {
        this.registrationMessageRepository = registrationMessageRepository;
    }

    @Operation(
            description = "Get endpoint for get all messages.",
            summary = "Get all messages.",
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
    @GetMapping
    public List<UserRegistrationMessage> getAllMessages(@RequestParam String companyName) {
            return registrationMessageRepository.findByCompanyName(companyName);
    }
}
