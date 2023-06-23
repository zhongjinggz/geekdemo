package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class Skill extends AuditableEntity {
    protected Long id;
    private Long tenantId;
    private Long skillTypeId;
    protected SkillLevel level;
    protected Integer duration;

    public Skill(Long tenantId, Long skillTypeId, Long createdBy) {
        super(LocalDateTime.now(), createdBy);
        this.tenantId = tenantId;
        this.skillTypeId = skillTypeId;
    }

    public Long getId() {
        return id;
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
}
