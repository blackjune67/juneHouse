package com.junehouse.response;

import lombok.Builder;

public record AuthResponse(User data, int error, String language, String msg, String code, String email, String message) {
    public record User(String language, UserData user) {
        // User 클래스 생성자나 다른 메서드가 필요한 경우 여기에 추가할 수 있습니다.
    }

    public record UserData(String email, String phone, String username, String id, String token) {
        // UserData 클래스 생성자나 다른 메서드가 필요한 경우 여기에 추가할 수 있습니다.
    }

    @Builder
    public AuthResponse {

    }
}
