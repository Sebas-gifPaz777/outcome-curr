package co.edu.icesi.dev.outcome_curr_mgmt.service.faculty.scheduler;

import co.edu.icesi.dev.outcome_curr_mgmt.service.faculty.AcadProgramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AcadProgramScheduler {

    private final AcadProgramService acadProgramService;

    public AcadProgramScheduler(AcadProgramService acadProgramService) {
        this.acadProgramService = acadProgramService;
    }

    @Scheduled(fixedRate = 60000) // Ejecuta cada 60 segundos
    public void scheduleGetAcadProgramsByFaculty() {
        try {
            log.info("Scheduler: Fetching academic programs by faculty.");
            acadProgramService.getAcadProgramsByFaculty(28);
        } catch (Exception ex) {
            log.error("Error during scheduled fetching of academic programs", ex);
        }
    }

    @Scheduled(fixedRate = 120000) // Ejecuta cada 120 segundos
    public void scheduleGetAcadProgram() {
        try {
            log.info("Scheduler: Fetching a specific academic program.");
            acadProgramService.getAcadProgram(28, 23);
        } catch (Exception ex) {
            log.error("Error during scheduled fetching of a specific program", ex);
        }
    }
}
