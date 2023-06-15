package demo.unjuanable.domain.orgmng.empnumcounter;

public interface EmpNumCounterRepository {
    void save(EmpNumCounter empNumCounter);

    EmpNumCounter findByYear(Long tenantId, int yearNum);

    Integer increaseMaxNumByYear(Long tenantId, int yearNum);
}
