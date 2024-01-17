package com.fasthopp.userservice.Auth;

import com.fasthopp.userservice.Dto.*;
import com.fasthopp.userservice.Repository.UserRepository;
import com.fasthopp.userservice.Entity.Role;
import com.fasthopp.userservice.Entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Endpoints")
public class AuthenticationController {

    private final AuthenticationService service;

    private final UserRepository repository;

    public static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING = "Authorization";

    @Operation(
            description = "Post endpoint for registering user as manager role.",
            summary = "Register new user as manager and creation of new workspace.",
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
    @PostMapping("/register-manager")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerManager(request));
    }

    @Operation(
            description = "Post endpoint for login a existing user.",
            summary = "Login a existing user.",
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
    @PostMapping("/login")
    public ResponseEntity<NewAuthResponse> login( @RequestBody LoginRequestDto request,
            HttpServletResponse response) throws IOException {

        AuthenticationResponse authResponse = service.authenticate(request);

        Optional<User> user = repository.findByEmail(request.getEmail());

        NewAuthResponse newAuth = null;
        if (user.isPresent()) {

            new UserDto();
            UserDto userDto = UserDto.builder()
                    .userId(user.get().getId())
                    .name(user.get().getName())
                    .email(user.get().getEmail())
                    .role(String.valueOf(user.get().getRole()))
                    .companyName(String.valueOf(user.get().getCompanyName().getName()))
                    .build();

            new NewAuthResponse();
            newAuth = NewAuthResponse.builder()
                    .userDto(userDto)
                    .accessToken(authResponse.getAccessToken())
                    .refreshToken(authResponse.getRefreshToken())
                    .build();

        }

        return ResponseEntity.ok(newAuth);
    }

    @Operation(
            description = "Post endpoint for refresh token.",
            summary = "Refresh token requesting.",
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
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

}
