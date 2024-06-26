package com.codersnextdoor.bringIt.api.user.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenResponseDTO {
    private String token;
    private Long userId;

    public TokenResponseDTO(String token, long userId) {
        this.token = token;
        this.userId = userId;
    }
}
