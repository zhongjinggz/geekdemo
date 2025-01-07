package demo.unjuanable.common.framework.domain;

import java.time.LocalDateTime;

public interface Auditable {
    Long getCreatedBy();

    LocalDateTime getCreatedAt();

    Long getLastUpdatedBy();

    Auditable setLastUpdatedBy(Long lastModifiedBy);

    LocalDateTime getLastUpdatedAt();

    Auditable setLastUpdatedAt(LocalDateTime lastUpdatedAt);
}
