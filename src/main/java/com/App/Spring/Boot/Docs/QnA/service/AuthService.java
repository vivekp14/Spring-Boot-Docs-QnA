package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.UserDTO;
import com.App.Spring.Boot.Docs.QnA.entity.TokenBlacklist;
import com.App.Spring.Boot.Docs.QnA.entity.User;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.TokenBlacklistRepository;
import com.App.Spring.Boot.Docs.QnA.repository.UserRepository;
import com.App.Spring.Boot.Docs.QnA.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final TokenBlacklistRepository blacklistRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, TokenBlacklistRepository blacklistRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.blacklistRepository = blacklistRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(UserDTO userDTO, String[] roles) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new ApiException("Username already exists", 400);
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Arrays.stream(roles).map(r -> r.toUpperCase()).toArray(String[]::new));
        userRepository.save(user);
    }

    public String login(@Valid UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new ApiException("User not found", 404));
        return jwtUtil.generateToken(userDTO.getUsername(), Arrays.asList(user.getRoles()));
    }

    public void logout(String token) {
        String jwt = token.replace("Bearer ", "");
        if (blacklistRepository.findByToken(jwt).isPresent()) {
            throw new ApiException("Token already blacklisted", 400);
        }
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(jwt);
        blacklist.setExpiryDate(LocalDateTime.now().plusHours(24));
        blacklistRepository.save(blacklist);
    }
}
