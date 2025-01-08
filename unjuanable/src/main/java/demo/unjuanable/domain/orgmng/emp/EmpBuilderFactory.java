package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.domain.common.validator.CommonOrgValidator;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpBuilderFactory {
    final EmpNumCounterRepository empNumCounter;
    final CommonOrgValidator assertOrg;


    @Autowired
    public EmpBuilderFactory(CommonOrgValidator assertOrg
    , EmpNumCounterRepository empNumCounter) {
        this.assertOrg = assertOrg;
        this.empNumCounter = empNumCounter;
    }

    public EmpBuilder builder() {
        return new EmpBuilder(assertOrg, empNumCounter);
    }

}
