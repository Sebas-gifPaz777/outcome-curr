package co.edu.icesi.dev.outcome_curr_mgmt.service.management.scheduler;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.management.LoginInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.management.LoginOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.service.management.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceScheduler {

    private final UserService userService;

    @Scheduled(fixedRate = 60000) // Ejecuta cada 60 segundos
    public void testLogin() {
        LoginInDTO loginInDTO = new LoginInDTO("testUser", "password123");
        try {
            LoginOutDTO loginOutDTO = userService.login(loginInDTO);
            log.info("Scheduler executed successfully: {}", loginOutDTO);
        } catch (Exception ex) {
            log.error("Error during scheduled login test", ex);
        }
    }
}
