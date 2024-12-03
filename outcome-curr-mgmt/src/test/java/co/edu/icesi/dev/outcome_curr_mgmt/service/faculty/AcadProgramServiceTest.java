package co.edu.icesi.dev.outcome_curr_mgmt.service.faculty;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import co.edu.icesi.dev.outcome_curr_mgmt.exception.OutCurrException;
import co.edu.icesi.dev.outcome_curr_mgmt.exception.OutCurrExceptionType;
import co.edu.icesi.dev.outcome_curr_mgmt.mapper.faculty.AcadProgramMapper;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.faculty.AcadProgram;
import co.edu.icesi.dev.outcome_curr_mgmt.persistence.faculty.AcadProgramRepository;
import co.edu.icesi.dev.outcome_curr_mgmt.service.faculty.scheduler.AcadProgramScheduler;
import co.edu.icesi.dev.outcome_curr_mgmt.service.provider.faculty.FacultyProvider;
import co.edu.icesi.dev.outcome_curr_mgmt.service.validator.faculty.AcadProgramValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

class AcadProgramServiceImplTest {

    @InjectMocks
    private AcadProgramServiceImpl acadProgramService;

    @Mock
    private AcadProgramValidator acadProgramValidator;

    @Mock
    private AcadProgramRepository acadProgramRepository;

    @Mock
    private FacultyProvider facultyProvider;

    @Mock
    private AcadProgramMapper acadProgramMapper;

    private AcadProgramScheduler acadProgramScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        acadProgramScheduler = new AcadProgramScheduler(acadProgramService);
    }

    @Test
    void testGetAcadProgramsByFaculty_Success() {
        long facultyId = 1L;

        // Mock behavior
        when(acadProgramRepository.findAllByFacultyFacId(facultyId))
                .thenReturn(List.of(new AcadProgram()));

        // Execute method
        List<AcadProgram> result = acadProgramService.getAcadProgramsByFaculty(facultyId);

        acadProgramScheduler.scheduleGetAcadProgram();
        acadProgramScheduler.scheduleGetAcadProgram();

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(acadProgramRepository, times(1)).findAllByFacultyFacId(facultyId);
    }

    @Test
    void testGetAcadProgramsByFaculty_NoPrograms() {
        long facultyId = 1L;

        // Mock behavior
        when(acadProgramRepository.findAllByFacultyFacId(facultyId)).thenReturn(Collections.emptyList());

        // Assertions
        OutCurrException exception = assertThrows(OutCurrException.class, () -> {
            acadProgramService.getAcadProgramsByFaculty(facultyId);
        });

        assertEquals(OutCurrExceptionType.FACULTY_INVALID_FAC_ID, exception.getOutCurrExceptionType());
        verify(acadProgramRepository, times(1)).findAllByFacultyFacId(facultyId);
    }

}
