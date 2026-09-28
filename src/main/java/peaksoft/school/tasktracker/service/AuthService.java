package peaksoft.school.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.school.tasktracker.dto.AuthResponse;
import peaksoft.school.tasktracker.dto.SignInRequest;
import peaksoft.school.tasktracker.dto.SignUpRequest;
import peaksoft.school.tasktracker.dto.UserResponse;
import peaksoft.school.tasktracker.entity.Role;
import peaksoft.school.tasktracker.entity.User;
import peaksoft.school.tasktracker.exception.EmailAlreadyExistsException;
import peaksoft.school.tasktracker.repository.UserRepository;
import peaksoft.school.tasktracker.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        User user = User.builder()
                .fullName(request.fullName().trim())
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .role(Role.DEVELOPER) // роль клиент выбирать не может
                .build();
        userRepository.save(user);

        return toAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse signIn(SignInRequest request) {
        String email = request.email().trim().toLowerCase();

        // при неверном пароле бросит BadCredentialsException -> 401 (см. GlobalExceptionHandler)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.password()));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return toAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(),
                user.getRole().name(), user.getCreatedAt());
    }

    private AuthResponse toAuthResponse(User user) {
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, "Bearer", user.getEmail(), user.getFullName(), user.getRole().name());
    }
}
