package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.*;


public abstract class AuditableEntity {
    protected ChangingStatus changingStatus = NEW;
    protected LocalDateTime createdAt;
    protected Long createdBy;
    protected LocalDateTime lastUpdatedAt;
    protected Long lastUpdatedBy;

    public AuditableEntity(LocalDateTime createdAt, Long createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public ChangingStatus getChangingStatus() {
        return changingStatus;
    }

    public void toUpdate() {
        this.changingStatus = UPDATED;
    }

    public void toDelete() {
        this.changingStatus = DELETED;
    }

    public void toUnChange() {
        this.changingStatus = UNCHANGED;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AuditableEntity setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public AuditableEntity setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

//    public void setLastUpdatedInfo(LocalDateTime lastUpdatedAt, Long lastUpdatedBy){
//        this.lastUpdatedAt = lastUpdatedAt;
//        this.lastUpdatedBy = lastUpdatedBy;
//    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

}
