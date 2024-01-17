package com.fasthopp.userservice.Controllers;


import com.fasthopp.userservice.Dto.ProfileGetDto;
import com.fasthopp.userservice.Dto.UserDto;
import com.fasthopp.userservice.Dto.UserDtoWithoutRole;
import com.fasthopp.userservice.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Endpoints")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            description = "Get endpoint for user profile.",
            summary = "Get all the details of the logged-in user's data in their profile.",
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
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProfileGetDto userProfileDto = userService.getUserProfile(authentication.getName());
        if (userProfileDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userProfileDto);
    }

    @Operation(
            description = "Patch endpoint for user profile for updating data.",
            summary = "Update all the details of the logged-in user's data in their profile.",
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
    @PatchMapping("/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileGetDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        ProfileGetDto updatedUserDto = userService.updateUserProfile(authentication.getName(), userDto);
        if (updatedUserDto == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUserDto);
    }
}
