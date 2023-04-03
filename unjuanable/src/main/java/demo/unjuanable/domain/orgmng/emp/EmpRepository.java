package demo.unjuanable.domain.orgmng.emp;

public interface EmpRepository {

    boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses);
}
