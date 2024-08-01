package enigma.toDoList.service;

import enigma.toDoList.model.User;
import enigma.toDoList.utils.dto.AuthDto;
import enigma.toDoList.utils.request.CreateSuperAdminRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    AuthDto.RegisterResponse register(AuthDto.RegisterRequest request);
    AuthDto.AuthenticationResponse authenticate(AuthDto.AuthenticationRequest request);
    AuthDto.RefreshTokenResponse refreshToken(AuthDto.RefreshTokenRequest request);


}
