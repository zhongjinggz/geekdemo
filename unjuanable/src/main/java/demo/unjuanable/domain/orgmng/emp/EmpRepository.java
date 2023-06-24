package demo.unjuanable.domain.orgmng.emp;

import java.util.Optional;

public interface EmpRepository {
    boolean save(Emp emp);

    boolean existByIdAndStatus(Long tenant, Long id, EmpStatus... statuses);

    Optional<Emp> findById(Long tenantId, Long id);
}
