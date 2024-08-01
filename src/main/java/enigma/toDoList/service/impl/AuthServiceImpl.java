package enigma.toDoList.service.impl;

import enigma.toDoList.exception.GlobalExceptionHandler;
import enigma.toDoList.model.Role;
import enigma.toDoList.model.User;
import enigma.toDoList.repository.UserRepository;
import enigma.toDoList.security.JwtService;
import enigma.toDoList.service.AuthService;
import enigma.toDoList.utils.dto.AuthDto;
import enigma.toDoList.utils.dto.UserDto;
import enigma.toDoList.utils.request.CreateSuperAdminRequest;
import enigma.toDoList.utils.response.RegisterResponse;
import enigma.toDoList.utils.response.Response;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    @Override
    public AuthDto.RegisterResponse register(AuthDto.RegisterRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        PasswordEncoder passwordEncoderAlt = new BCryptPasswordEncoder();

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        User savedUser = repository.save(user);
        savedUser.setPassword(null);

        AuthDto.RegisterResponse response = new AuthDto.RegisterResponse(
                savedUser.getId().toString(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return response;
    }

    @Override
    public AuthDto.AuthenticationResponse authenticate(AuthDto.AuthenticationRequest request) {
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        }catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->  new RuntimeException("User with this email not found"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthDto.AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthDto.RefreshTokenResponse refreshToken(AuthDto.RefreshTokenRequest request) {

        var user = jwtService.extractAllClaims(request.getRefreshToken());
        var userDetails = repository.findByEmail(user.getSubject()).orElseThrow(() -> new RuntimeException("User with this email not found"));
        var jwtToken = jwtService.isTokenValid(request.getRefreshToken(), userDetails);
        if (!jwtToken) {
            throw  new RuntimeException("Invalid refresh token");
        }
            var accessToken = jwtService.generateToken(userDetails);

        return AuthDto.RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }





}
