package com.fasthopp.userservice.Controllers;

import com.fasthopp.userservice.Auth.AuthenticationService;
import com.fasthopp.userservice.Dto.ProfileGetDto;
import com.fasthopp.userservice.Dto.RegisterRequest;
import com.fasthopp.userservice.Dto.UserDto;
import com.fasthopp.userservice.Entity.Role;
import com.fasthopp.userservice.Service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@Tag(name = "Manager Endpoints")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AuthenticationService authService;


    @Operation(
            description = "Post endpoint for create new User by manager.",
            summary = "Create new User by manager.",
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
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest user) {
        var authResponse = managerService.register(user, Role.USER);
        if (authResponse == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Operation(
            description = "Post endpoint for create new Manager by manager.",
            summary = "Create new Manager by a existing manager.",
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
    @PostMapping("/create-manager")
    public ResponseEntity<?> createManager(@RequestBody RegisterRequest user) {
        var authResponse = managerService.register(user, Role.MANAGER);
        if (authResponse == null) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Operation(
            description = "Get endpoint for get all the users with Manager role and User role for manager.",
            summary = "Get all the users with Manager role and User role for manager.",
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
    @GetMapping("/all-participants")
    public ResponseEntity<List<ProfileGetDto>> getAllParticipants(){
        var allUsers = managerService.getAllParticipants();
        System.out.println("all users ");
        System.out.println(allUsers);
        System.out.println("all users from backend");
        return ResponseEntity.ok(allUsers);
    }

    @Operation(
            description = "Get endpoint for get all the users with User role for manager.",
            summary = "Get all the users with User role for manager.",
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
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        var allUsers = managerService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @Operation(
            description = "Get endpoint for get all the users with Manager role for manager.",
            summary = "Get all the users with Manager role for manager.",
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
    @GetMapping("/all-managers")
    public ResponseEntity<List<UserDto>> getAllManagers(){
        var allUsers = managerService.getAllManagers();
        return ResponseEntity.ok(allUsers);
    }


    @Operation(
            description = "Delete endpoint for delete a user by ID.",
            summary = "Delete a user by ID.",
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
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        managerService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Patch endpoint for block a user by user ID.",
            summary = "Block a user by user ID.",
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
    @PatchMapping("/user/block/{userId}")
    public ResponseEntity<Void> blockUser(@PathVariable int userId) {
        managerService.blockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Patch endpoint for unblock a user by user ID.",
            summary = "Unblock a user by user ID.",
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
    @PatchMapping("/user/unblock/{userId}")
    public ResponseEntity<Void> unblockUser(@PathVariable int userId) {
        managerService.unblockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Get endpoint for user details by ID.",
            summary = "Get user details by ID for manager role.",
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
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable int userId){
        UserDto userDto = managerService.findById(userId);
        if(userDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            description = "Put endpoint for update user details by user ID.",
            summary = "Update user details by user ID.",
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
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId,@RequestBody UserDto userDto){
        UserDto theUserDto = managerService.updateUser(userId, userDto);
        if(theUserDto == null) return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.CREATED).body(theUserDto);
    }


}
