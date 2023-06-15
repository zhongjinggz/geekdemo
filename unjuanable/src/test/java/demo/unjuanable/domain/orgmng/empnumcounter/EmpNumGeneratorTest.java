package demo.unjuanable.domain.orgmng.empnumcounter;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterRepositoryJdbc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpNumGeneratorTest {
    @Mock
    EmpNumCounterRepositoryJdbc mockedempNumCounterRepositoryJdbc;
    @Spy
    @InjectMocks
    EmpNumGenerator spyEmpNumGenerator;

    @Test
    void generateEmpNum_shouldGenerateEmpNum() {
        doReturn("YYYY00000001").when(spyEmpNumGenerator).generateEmpNumByYear(anyLong(), anyInt());
        String empNum = spyEmpNumGenerator.generateEmpNum(1L);
        verify(spyEmpNumGenerator).generateEmpNumByYear(1L, LocalDate.now().getYear());
        assertEquals("YYYY00000001", empNum);
    }
}