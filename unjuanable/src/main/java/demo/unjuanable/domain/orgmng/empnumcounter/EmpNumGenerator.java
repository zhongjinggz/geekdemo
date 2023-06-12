package demo.unjuanable.domain.orgmng.empnumcounter;

import demo.unjuanable.adapter.driven.persistence.orgmng.EmpNumCounterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpNumGenerator {
    // inject EmpNumCounterDao by constructor
    final EmpNumCounterDao empNumCounterDao;

    @Autowired
    public EmpNumGenerator(EmpNumCounterDao empNumCounterDao) {
        this.empNumCounterDao = empNumCounterDao;
    }

    public String generateEmpNum() {
        return null;
    }

    String generateEmpNumByYear(Long tenantId, int yearNum) {
        int maxNum = empNumCounterDao.selectMaxNumByYear(tenantId, yearNum);
        return String.format("%04d%08d", yearNum, maxNum);
    }
}
