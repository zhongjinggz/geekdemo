package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public class AggregateRoot
        extends AuditableEntity implements Versionable {
    protected Long version;

    public AggregateRoot(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.changingStatus = ChangingStatus.NEW;
        this.version = 0L;
    }

    @Override
    public Long getVersion() {
        return version;
    }
}
