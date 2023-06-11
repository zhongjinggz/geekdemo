package demo.unjuanable.domain.orgmng.emp;

public interface EmpRepository {

    boolean save(Emp emp);

    boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses);
}
