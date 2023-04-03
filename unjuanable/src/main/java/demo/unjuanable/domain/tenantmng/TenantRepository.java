package demo.unjuanable.domain.tenantmng;

public interface TenantRepository {
    boolean existsByIdAndStatus(long tenantId, TenantStatus status);
}
