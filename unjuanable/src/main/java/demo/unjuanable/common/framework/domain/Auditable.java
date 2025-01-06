package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public interface Auditable<T extends Auditable<T>> {
    Long getCreatedBy();

    LocalDateTime getCreatedAt();

    Long getLastUpdatedBy();

    T setLastUpdatedBy(Long lastModifiedBy);

    LocalDateTime getLastUpdatedAt();

    T setLastUpdatedAt(LocalDateTime lastUpdatedAt);
}
