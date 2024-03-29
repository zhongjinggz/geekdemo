package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public abstract class AuditableEntity {
    protected final LocalDateTime createdAt;
    protected final Long createdBy;
    protected LocalDateTime lastUpdatedAt;
    protected Long lastUpdatedBy;

    public AuditableEntity(LocalDateTime createdAt, Long createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
