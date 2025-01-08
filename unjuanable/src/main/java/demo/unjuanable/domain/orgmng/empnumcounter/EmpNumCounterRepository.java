package demo.unjuanable.domain.orgmng.empnumcounter;

import java.util.Optional;

public interface EmpNumCounterRepository {
    EmpNumCounter save(EmpNumCounter empNumCounter);

    Optional<EmpNumCounter> findByYear(Long tenantId, int yearNum);

    Integer increaseMaxNumByYear(Long tenantId, int yearNum);
}
