package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.common.framework.exception.BusinessException;
import java.util.Arrays;

public enum EmpStatus {
    REGULAR("REG"),           // 正式
    PROBATION("PRO"),       // 试用期
    TERMINATED("TER");        // 终止

    private final String code;

    EmpStatus(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static EmpStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException("员工状态代码错误！"));
    }

    public EmpStatus becomeRegular() {
        if (this != PROBATION) {
            throw new BusinessException("试用期员工才能转正！");
        }
        return REGULAR;
    }

    public EmpStatus terminate() {
        if (this == TERMINATED) {
            throw new BusinessException("已经终止的员工不能再次终止！");
        }
        return TERMINATED;
    }
}

// unit test for above code with Junit5
//package demo.unjuanable.domain.orgmng.emp;
//
//@Test
//public class EmpStatusTest {
//
//    @Test
//    public void testOfCode() {
//        assertEquals(EmpStatus.REGULAR, EmpStatus.ofCode("REG"));
//        assertEquals(EmpStatus.PROBATION, EmpStatus.ofCode("PRO"));
//        assertEquals(EmpStatus.TERMINATED, EmpStatus.ofCode("TER"));
//    }
//
//    @Test
//    public void testBecomeRegular() {
//        assertEquals(EmpStatus.REGULAR, EmpStatus.PROBATION.becomeRegular());
//        assertThrows(BusinessException.class, () -> EmpStatus.REGULAR.becomeRegular());
//        assertThrows(BusinessException.class, () -> EmpStatus.TERMINATED.becomeRegular());
//    }
//
//    @Test
//    public void testTerminate() {
//        assertEquals(EmpStatus.TERMINATED, EmpStatus.REGULAR.terminate());
//        assertEquals(EmpStatus.TERMINATED, EmpStatus.PROBATION.terminate());
//        assertThrows(BusinessException.class, () -> EmpStatus.TERMINATED.terminate());
//    }
//}

