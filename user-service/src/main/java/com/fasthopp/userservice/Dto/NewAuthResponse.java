package com.fasthopp.userservice.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasthopp.userservice.Dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAuthResponse {

    @JsonProperty("access")
    private String accessToken;
    @JsonProperty("refresh")
    private String refreshToken;

    @JsonProperty("user")
    private UserDto userDto;

}
