package demo.unjuanable.domain.tenantmng;

import demo.unjuanable.common.framework.domain.AuditableEntity;
import demo.unjuanable.domain.orgmng.org.OrgStatus;

import java.time.LocalDateTime;

public class Tenant extends AuditableEntity {
    private Long id;
    private String name;
    private TenantStatus status;

    public Tenant(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        status = TenantStatus.EFFECTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public TenantStatus getStatus() {
        return status;
    }
}
