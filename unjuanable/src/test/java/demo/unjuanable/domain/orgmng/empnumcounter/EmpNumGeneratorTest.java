package demo.unjuanable.domain.orgmng.empnumcounter;

import demo.unjuanable.adapter.driven.persistence.orgmng.empnumcounter.EmpNumCounterRepositoryJdbc;
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
        EmpNumGenerator empNumGenerator1 = doReturn("YYYY00000001").when(spyEmpNumGenerator);
        Long tenantId = anyLong();
        int yearNum1 = anyInt();
        int maxNum2 = empNumGenerator1.empNumCounterRepositoryJdbc.increaseMaxNumByYear(tenantId, yearNum1);
        int yearNum = LocalDate.now().getYear();
        int maxNum1 = spyEmpNumGenerator.empNumCounterRepositoryJdbc.increaseMaxNumByYear(1L, yearNum);
        String empNum = (String.format("%04d%08d", yearNum, maxNum1));
        EmpNumGenerator empNumGenerator = verify(spyEmpNumGenerator);
        int maxNum = empNumGenerator.empNumCounterRepositoryJdbc.increaseMaxNumByYear(1L, LocalDate.now().getYear());
        assertEquals("YYYY00000001", empNum);
    }
}