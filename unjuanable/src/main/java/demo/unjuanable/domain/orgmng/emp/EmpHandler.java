package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterDao;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpHandler {
    final EmpNumCounterDao empNumCounterDao;

    @Autowired
    public EmpHandler(EmpNumCounterDao empNumCounterDao) {
        this.empNumCounterDao = empNumCounterDao;
    }


    public String generateEmpNum() {
        return null;
    }

    String generateEmpNumByYear(int year) {

        return null;
    }
}
