package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterRepositoryJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpHandler {
    final EmpNumCounterRepositoryJdbc empNumCounterRepositoryJdbc;

    @Autowired
    public EmpHandler(EmpNumCounterRepositoryJdbc empNumCounterRepositoryJdbc) {
        this.empNumCounterRepositoryJdbc = empNumCounterRepositoryJdbc;
    }


    public String generateEmpNum() {
        return null;
    }

    String generateEmpNumByYear(int year) {

        return null;
    }
}
