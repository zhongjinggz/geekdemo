package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public class AggregateRoot extends AuditableEntity {
    protected Long version;

    public AggregateRoot(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
    }

    public Long getVersion() {
        return version;
    }
}
