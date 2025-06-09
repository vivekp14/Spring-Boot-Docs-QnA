package com.App.Spring.Boot.Docs.QnA.service;
import com.App.Spring.Boot.Docs.QnA.dto.LoginRequest;
import com.App.Spring.Boot.Docs.QnA.dto.LoginResponse;
import com.App.Spring.Boot.Docs.QnA.entity.TokenBlacklist;
import com.App.Spring.Boot.Docs.QnA.entity.User;
import com.App.Spring.Boot.Docs.QnA.repository.TokenBlacklistRepository;
import com.App.Spring.Boot.Docs.QnA.repository.UserRepository;
import com.App.Spring.Boot.Docs.QnA.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenBlacklistRepository tokenBlacklistRepository;
    @Mock
    private JwtUtil jwtUtil;

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = authService.register("test", "password", Set.of("VIEWER"));
        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("$2a$10$examplehash");
        user.setRoles(Set.of("VIEWER"));
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken("test", Set.of("VIEWER"))).thenReturn("jwt-token");
        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("password");
        LoginResponse result = authService.login(request);
        assertNotNull(result);
        assertEquals("test", result.getUsername());
        assertEquals("jwt-token", result.getToken());
    }

    @Test
    public void testLogout() {
        authService.logout("jwt-token");
        verify(tokenBlacklistRepository, times(1)).save(any(TokenBlacklist.class));
    }
}