package demo.unjuanable.domain.tenantmng;

import demo.unjuanable.common.framework.domain.AggregateRoot;
import demo.unjuanable.common.framework.domain.AuditableEntity;
import demo.unjuanable.common.framework.domain.ChangingStatus;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.*;

public class Tenant extends AggregateRoot {
    private Long id;
    private String name;
    private TenantStatus status;

    public Tenant(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.status = TenantStatus.EFFECTIVE;
        this.version = 1L;
        this.changingStatus = NEW;
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
