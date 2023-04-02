package demo.unjuanable.domain.orgmng.orgtype;

public interface OrgTypeRepository {
    boolean existsByCodeAndStatus(long tenant, String code, OrgTypeStatus status);
}
