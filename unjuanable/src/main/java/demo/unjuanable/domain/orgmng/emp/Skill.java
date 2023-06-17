package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class Skill extends AuditableEntity {
    protected Long id;
    private Long tenantId;
    private Long skillTypeId;
    protected SkillLevel level;
    protected int duration;

    protected Skill(Long tenantId, Long skillTypeId, Long createdBy) {
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

    public int getDuration() {
        return duration;
    }

    Skill setDuration(int duration) {
        this.duration = duration;
        return this;
    }
}