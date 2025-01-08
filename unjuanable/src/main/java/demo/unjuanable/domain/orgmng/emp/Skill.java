package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.UNCHANGED;

public class Skill extends AuditableEntity {
    private Emp emp;
    protected Long id;
    final private Long tenantId;
    final private Long skillTypeId;
    protected SkillLevel level;
    protected Integer duration;

    // 用户新建
    Skill(Emp emp, Long tenantId, Long skillTypeId, Long createdBy) {
        super(LocalDateTime.now(), createdBy);
        this.emp = emp;
        this.tenantId = tenantId;
        this.skillTypeId = skillTypeId;
    }

    // 用于从数据库中加载
    public Skill(Long id
            , Long tenantId
            , Long skillTypeId
            , String levelCode
            , Integer duration
            , Long createdBy
            , LocalDateTime createdAt
            , Long lastUpdatedBy
            , LocalDateTime lastUpdatedAt
    ) {
        super(LocalDateTime.now(), createdBy);
        this.changingStatus = UNCHANGED;

        this.emp = emp;

        this.id = id;
        this.tenantId = tenantId;
        this.skillTypeId = skillTypeId;
        this.level = SkillLevel.ofCode(levelCode);
        this.duration = duration;
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

    public Long getSkillTypeId() {
        return skillTypeId;
    }

    public SkillLevel getLevel() {
        return level;
    }

    Skill setLevel(SkillLevel level) {
        this.level = level;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    Skill setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    Skill setEmp(Emp emp) {
        this.emp = emp;
        return this;
    }

}
