package demo.unjuanable.domain.orgmng.empnumcounter;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterRepositoryJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmpNumGenerator {
    // inject EmpNumCounterDao by constructor
    final EmpNumCounterRepositoryJdbc empNumCounterRepositoryJdbc;

    @Autowired
    public EmpNumGenerator(EmpNumCounterRepositoryJdbc empNumCounterRepositoryJdbc) {
        this.empNumCounterRepositoryJdbc = empNumCounterRepositoryJdbc;
    }

    public String generateEmpNum(Long tenantId) {
        return(generateEmpNumByYear(tenantId, LocalDate.now().getYear()));
    }



    String generateEmpNumByYear(Long tenantId, int yearNum) {
        int maxNum = empNumCounterRepositoryJdbc.increaseMaxNumByYear(tenantId, yearNum);
        return String.format("%04d%08d", yearNum, maxNum);
    }
}
