package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.common.valueobject.Period;

import static demo.unjuanable.common.framework.domain.ChangingStatus.UNCHANGED;

public class WorkExperience extends AuditableEntity {
    private Long id;
    private Emp emp;
    final private Long tenantId;
    final private Period period;
    protected String company;

    protected WorkExperience(Emp emp, Long tenantId, Period period
            , LocalDateTime createdAt, Long createdBy) {

        super(createdAt, createdBy);
        this.emp = emp;
        this.tenantId = tenantId;
        this.period = period;
    }

    // to be deleted
    protected WorkExperience(Emp emp, Long tenantId, Long id, Period period
            , LocalDateTime createdAt, Long createdBy) {

        this(emp, tenantId, period, createdAt, createdBy);
        this.id = id;
    }

    // 用于从数据库加载
    public WorkExperience(Long tenantId
            , Long id
            , LocalDate startDate
            , LocalDate endDate
            , String company
            , LocalDateTime createdAt
            , Long createdBy
            , LocalDateTime lastUpdatedAt
            , Long lastUpdatedBy
    ) {
        super(createdAt, createdBy);
        this.changingStatus = UNCHANGED;
        this.emp = emp;

        this.tenantId = tenantId;
        this.id = id;
        this.period = Period.of(startDate, endDate);
        this.company = company;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getId() {
        return id;
    }

    public Long getEmpId() {
        return emp.getId();
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

    public void setEmp(Emp emp) {
        this.emp = emp;
    }
}
