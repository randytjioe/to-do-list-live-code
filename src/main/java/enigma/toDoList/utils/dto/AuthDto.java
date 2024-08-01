package enigma.toDoList.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationRequest {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResponse {
        private String id;
        private String username;
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthenticationResponse {
        private String accessToken;
        private String refreshToken;
        }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshTokenRequest {
        private String refreshToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class RefreshTokenResponse {
        private String accessToken;

    }
    }


