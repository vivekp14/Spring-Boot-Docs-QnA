package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.UserDTO;
import com.App.Spring.Boot.Docs.QnA.entity.User;
import com.App.Spring.Boot.Docs.QnA.exception.ApiException;
import com.App.Spring.Boot.Docs.QnA.repository.TokenBlacklistRepository;
import com.App.Spring.Boot.Docs.QnA.repository.UserRepository;
import com.App.Spring.Boot.Docs.QnA.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private TokenBlacklistRepository blacklistRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");

        user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hashedPassword");
        user.setRoles(new String[]{"USER"});
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        authService.register(userDTO, new String[]{"USER"});
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUsernameExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        assertThrows(ApiException.class, () -> authService.register(userDTO, new String[]{"USER"}));
    }

    @Test
    void testLoginSuccess() {
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", "password123");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken("testuser", anyList())).thenReturn("jwt-token");
        String token = authService.login(userDTO);
        assertEquals("jwt-token", token);
    }

    @Test
    void testLogoutSuccess() {
        when(blacklistRepository.findByToken("jwt-token")).thenReturn(Optional.empty());
        authService.logout("Bearer jwt-token");
        verify(blacklistRepository).save(any());
    }
}
