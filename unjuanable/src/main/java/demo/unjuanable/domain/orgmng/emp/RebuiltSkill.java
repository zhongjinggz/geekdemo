package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.common.framework.domain.ChangingStatus;

public class RebuiltSkill extends Skill {
    public RebuiltSkill(Long tenantId, Long id, Long skillTypeId, Long createdBy) {
        super(tenantId, skillTypeId, createdBy);
        this.id = id;
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    RebuiltSkill resetLevel(SkillLevel level) {
        this.level = level;
        return this;
    }

    RebuiltSkill resetDuration(Integer duration) {
        this.duration = duration;
        return this;
    }
}
