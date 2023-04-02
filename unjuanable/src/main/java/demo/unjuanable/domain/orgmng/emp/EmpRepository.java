package demo.unjuanable.domain.orgmng.emp;

public interface EmpRepository {
    int countByIdAndStatus(long tenantId, long id, EmpStatus... statuses);

    boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses);
}
