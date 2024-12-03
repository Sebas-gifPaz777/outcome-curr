package co.edu.icesi.dev.outcome_curr_mgmt.rs.management;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.management.LoginInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.management.LoginOutDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.rs.management.AuthUserController;
import co.edu.icesi.dev.outcome_curr_mgmt.service.management.UserService;
import co.edu.icesi.dev.outcome_curr_mgmt.service.management.scheduler.UserServiceScheduler;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthUserControllerImpl implements AuthUserController {
    private final UserService userService;
    private final MeterRegistry meterRegistry;
    @Override
    public LoginOutDTO login(LoginInDTO loginDTO) {
        log.info("Starting Login process, with user: {}", loginDTO.username());
        meterRegistry.counter("login.attempts", "username", loginDTO.username()).increment();
        UserServiceScheduler userTest = new UserServiceScheduler(userService);
        userTest.testLogin();
        return userService.login(loginDTO);
    }
}
