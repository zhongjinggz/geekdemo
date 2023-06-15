package demo.unjuanable.domain.orgmng.emp;

import java.util.Optional;

public interface EmpRepository {
    boolean save(Emp emp);

    int countByIdAndStatus(long tenantId, long id, EmpStatus... statuses);

    boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses);

    Optional<Emp> findById(Long tenantId, Long id);
}
