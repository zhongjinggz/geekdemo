package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.common.framework.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// unit test for EmpStatus.java with Junit5
class EmpStatusTest {
    @Test
    void ofCode_shouldGenerateStatusByCode() {
        assertEquals(EmpStatus.REGULAR, EmpStatus.ofCode("REG"));
        assertEquals(EmpStatus.PROBATION, EmpStatus.ofCode("PRO"));
        assertEquals(EmpStatus.TERMINATED, EmpStatus.ofCode("TER"));
    }

    @Test
    void becomeRegular() {
        assertEquals(EmpStatus.REGULAR, EmpStatus.PROBATION.becomeRegular());
        assertThrows(BusinessException.class, EmpStatus.REGULAR::becomeRegular);
        assertThrows(BusinessException.class, EmpStatus.TERMINATED::becomeRegular);
    }

    @Test
    void testTerminate() {
        assertEquals(EmpStatus.TERMINATED, EmpStatus.REGULAR.terminate());
        assertEquals(EmpStatus.TERMINATED, EmpStatus.PROBATION.terminate());
        assertThrows(BusinessException.class, EmpStatus.TERMINATED::terminate);
    }

}