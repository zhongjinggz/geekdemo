package demo.unjuanable.domain.orgmng.empnumcounter;

import java.util.Optional;

public interface EmpNumCounterRepository {
    EmpNumCounter save(EmpNumCounter empNumCounter);

    Optional<EmpNumCounter> findByYear(Long tenantId, int yearNum);

    Integer nextNumByYear(Long tenantId, int yearNum);
}
