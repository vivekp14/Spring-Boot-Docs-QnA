package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.LoginRequest;
import com.App.Spring.Boot.Docs.QnA.dto.LoginResponse;
import com.App.Spring.Boot.Docs.QnA.entity.TokenBlacklist;
import com.App.Spring.Boot.Docs.QnA.entity.User;
import com.App.Spring.Boot.Docs.QnA.repository.TokenBlacklistRepository;
import com.App.Spring.Boot.Docs.QnA.repository.UserRepository;
import com.App.Spring.Boot.Docs.QnA.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    public User register(String username, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
            return new LoginResponse(token, user.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }

    public void logout(String token) {
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setBlacklistedAt(LocalDateTime.now());
        tokenBlacklistRepository.save(blacklist);
    }

    public boolean isTokenBlacklisted(String token) {

        return tokenBlacklistRepository.existsByToken(token);
    }
}
