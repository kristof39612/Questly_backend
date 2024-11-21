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

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
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
        String username = request.getUsername();
        if (username != null && usernameTakenInClientOrCoach(username)){
            return throwUsernameTakenError();
        }
        try {
            String input = request.getPassword();
            //SecretKey key = DecryptUtility.generateKey(128);
            byte[] decodedKey = Base64.getDecoder().decode("pQjbshoJGc3EAkRaa5FXsA==");
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            byte[] iv = DecryptUtility.readIV();
            String algorithm = "AES/CBC/PKCS5Padding";
            //IvParameterSpec ivParameterSpec = DecryptUtility.generateIv();
            //String cipherText = DecryptUtility.encrypt(algorithm, input, key, new IvParameterSpec(iv));
            //String plainText = DecryptUtility.decrypt(algorithm, cipherText, key, new IvParameterSpec(iv));
            String plainText = DecryptUtility.decrypt(algorithm, input, key, new IvParameterSpec(iv));
            System.out.println(plainText);
        }catch (Exception e){
            System.out.println("Error in the decryption of password");;
        }


        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCurrentTaskPointId(null);
        user.setRole(Role.USER);
        userRepository.save(user);
        String token = jwtService.generateToken(this.createExtraClaims(user),user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse loginUserEmail(LoginRequest request) {
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
        return userService.userEmailExists(email);
    }

    public boolean usernameTakenInClientOrCoach(String username){
        return userService.userNameExists(username);
    }

    public AuthResponse throwEmailTakenError(){
        return AuthResponse.builder().errorMessage("Email already taken").build();
    }

    public AuthResponse throwUsernameTakenError(){
        return AuthResponse.builder().errorMessage("Username already taken").build();
    }
}
