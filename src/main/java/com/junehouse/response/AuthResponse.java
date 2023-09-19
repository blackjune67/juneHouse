package com.junehouse.response;

import lombok.Builder;

public record AuthResponse(String code, String email, String message) {
    @Builder
    public AuthResponse {

    }
}
