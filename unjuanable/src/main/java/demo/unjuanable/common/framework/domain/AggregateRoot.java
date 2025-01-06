package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public class AggregateRoot<T extends AuditableEntity<T>>
        extends AuditableEntity<T> implements Versionable {
    protected Long version;

    public AggregateRoot(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
    }

    @Override
    public Long getVersion() {
        return version;
    }
}
