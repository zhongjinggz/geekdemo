package demo.unjuanable.domain.orgmng.empnumcounter;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterRepositoryJdbc;
import demo.unjuanable.common.framework.exception.SystemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EmpNumGeneratorIT {
    // table structure:
    //create table emp_num_counter
    //(
    //    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    //    year_num        int            not null,
    //    max_emp_num     int            not null,
    //    primary key (tenant_id, year_num)
    //);

    @Autowired
    EmpNumCounterRepositoryJdbc empNumCounterRepositoryJdbc;

    @Autowired
    EmpNumGenerator empNumGenerator;

    @Test
    void generateEmpNumByYear_returnEmpNum_whenCounterExists() {
        EmpNumCounter empNumCounter = new EmpNumCounter(1, 9999, 100);
        empNumCounterRepositoryJdbc.save(empNumCounter);

        String actualEmpNum = empNumGenerator.generateEmpNumByYear(1L, 9999);
        assertEquals("999900000101", actualEmpNum);

        EmpNumCounter actualEmpNumCounter = empNumCounterRepositoryJdbc.findByYear(1L, 9999);
        assertEquals(101, actualEmpNumCounter.getMaxEmpNum());

    }

   // test case : generateEmpNumByYear will throw SystemException when counter not exists
    @Test
    void generateEmpNumByYear_throwSystemException_whenCounterNotExists() {
        Exception e = assertThrows(SystemException.class
                , () -> empNumGenerator.generateEmpNumByYear(1L, 9999));

        assertEquals("租户ID为'1'的年份为'9999'的员工编号计数器不存在！", e.getMessage());
    }
}