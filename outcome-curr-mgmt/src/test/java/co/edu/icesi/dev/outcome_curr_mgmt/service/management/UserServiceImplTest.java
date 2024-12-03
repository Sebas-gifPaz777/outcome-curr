package co.edu.icesi.dev.outcome_curr_mgmt.service.management;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.management.LoginInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.management.LoginOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.exception.OutCurrException;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.management.User;
import co.edu.icesi.dev.outcome_curr_mgmt.persistence.management.UserRepository;
import co.edu.icesi.dev.outcome_curr_mgmt.saamfi.delegate.SaamfiClient;
import co.edu.icesi.dev.outcome_curr_mgmt.saamfi.util.SaamfiJwtTools;
import co.edu.icesi.dev.outcome_curr_mgmt.service.management.scheduler.UserServiceScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SaamfiClient saamfiClient;

    @Mock
    private SaamfiJwtTools saamfiJwtTools;

    @Autowired
    private UserServiceScheduler userServiceScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userServiceScheduler = new UserServiceScheduler(userService);
    }

    @Test
    void testLoginSuccess() {
        // Mocking dependencies
        LoginInDTO loginInDTO = new LoginInDTO("testUser", "password123");
        LoginOutDTO loginOutDTO = new LoginOutDTO(1, "test", "user", "test@example.com", "3", "username", "lastname", "id", "testToken", "type", "system");

        when(saamfiClient.getUserLogin(loginInDTO)).thenReturn(loginOutDTO);
        when(saamfiJwtTools.tokenHasPermission("testToken", "ROLE_Access-system")).thenReturn(true);
        when(userRepository.findById(Long.parseLong("1"))).thenReturn(Optional.empty());

        // Simulating a new user save
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));

        // Execute the method
        LoginOutDTO result = userService.login(loginInDTO);

        // userServiceScheduler.testLogin();

        // Validate results
        assertNotNull(result);
        assertEquals(Long.parseLong("1"), result.userId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLoginInvalidPermission() {
        LoginInDTO loginInDTO = new LoginInDTO("testUser", "password123");
        LoginOutDTO loginOutDTO = new LoginOutDTO(1, "test", "user", "test@example.com", "3", "username", "lastname", "id", "token", "type", "system");

        when(saamfiClient.getUserLogin(loginInDTO)).thenReturn(loginOutDTO);
        when(saamfiJwtTools.tokenHasPermission("testToken", "ROLE_Access-system")).thenReturn(false);

        // Execute the method and expect an exception
        assertThrows(OutCurrException.class, () -> userService.login(loginInDTO));
    }
}
