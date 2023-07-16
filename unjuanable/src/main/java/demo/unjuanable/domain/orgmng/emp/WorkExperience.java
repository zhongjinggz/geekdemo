package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;
import demo.unjuanable.domain.common.valueobject.Period;

public class WorkExperience extends AuditableEntity {
    private Long id;
    final private Long tenantId;
    final private Period period;
    protected String company;

    protected WorkExperience(Long tenantId, Period period
            , LocalDateTime createdAt, Long createdBy) {

        super(createdAt, createdBy);
        this.tenantId = tenantId;
        this.period = period;
    }

    protected WorkExperience(Long tenantId, Long id, Period period
            , LocalDateTime createdAt, Long createdBy) {

        this(tenantId, period, createdAt, createdBy);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Period getPeriod() {
        return this.period;
    }

    public String getCompany() {
        return company;
    }

    WorkExperience setCompany(String company) {
        this.company = company;
        return this;
    }

}
