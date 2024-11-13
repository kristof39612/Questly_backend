package com.questly.questly_backend.auth;

import com.questly.questly_backend.config.JwtService;
import com.questly.questly_backend.model.User.User;
import com.questly.questly_backend.model.User.Role;
import com.questly.questly_backend.model.User.UserRepository;
import com.questly.questly_backend.model.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final UserService userService;


    public AuthResponse registerUser(RegisterRequest request) {
        if (emailTakenInClientOrCoach(request.getEmail())){
            return throwEmailTakenError();
        }


        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCurrentTaskPointId(null);
        user.setRole(Role.USER);
        userRepository.save(user);
        String token = jwtService.generateToken(this.createExtraClaims(user),user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse loginUser(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User client = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(this.createExtraClaims(client),client);
        return AuthResponse.builder().token(token).build();
    }

    public HashMap<String,String>createExtraClaims(User user){
        HashMap<String,String> claims = new HashMap<>();
        claims.put("role",user.getRole().toString());
        claims.put("email",user.getEmail());
        return claims;
    }

    public boolean emailTakenInClientOrCoach(String email){
        return userService.userExists(email);
    }

    public AuthResponse throwEmailTakenError(){
        return AuthResponse.builder().errorMessage("Email already taken").build();
    }
}
