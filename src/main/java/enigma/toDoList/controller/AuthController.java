package enigma.toDoList.controller;

import enigma.toDoList.service.AuthService;
import enigma.toDoList.utils.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthDto.RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthenticationResponse> authenticate(
            @RequestBody AuthDto.AuthenticationRequest request
    ) {

        if (authService.authenticate(request) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto.RefreshTokenResponse> refresh(
            @RequestBody AuthDto.RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authService.refreshToken(request)    );
    }



}
