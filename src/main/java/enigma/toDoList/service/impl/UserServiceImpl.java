package enigma.toDoList.service.impl;

import enigma.toDoList.model.Role;
import enigma.toDoList.model.User;
import enigma.toDoList.repository.UserRepository;
import enigma.toDoList.service.UserService;
import enigma.toDoList.utils.dto.UserDto;
import enigma.toDoList.utils.request.CreateSuperAdminRequest;
import enigma.toDoList.utils.request.UpdateRoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${application.security.jwt.super_admin_secret_key}")
    private String superAdminSecretKey;

    @Value("${application.security.jwt.admin_secret_key}")
    private String adminSecretKey;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Page<User> getAll(Pageable pageable) {

        return userRepository.findAll( pageable);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User changeUserRole(Integer id, UpdateRoleRequest updatedUser, String secretKey) {
        if (!adminSecretKey.equals(secretKey)) {
            throw new RuntimeException("Invalid Admin Secret Key");
        }
        User user = getById(id);
        user.setRole(Role.valueOf(updatedUser.getRole()));
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User createSuperAdmin(CreateSuperAdminRequest request, String secretKey) {
        if (!superAdminSecretKey.equals(secretKey)) {
            throw new RuntimeException("Invalid Super Admin Secret Key");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.SUPER_ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        return user;
    }
}
