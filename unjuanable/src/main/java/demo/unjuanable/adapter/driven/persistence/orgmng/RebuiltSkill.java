package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.orgmng.emp.Skill;
import demo.unjuanable.domain.orgmng.emp.SkillLevel;

public class RebuiltSkill extends Skill {
    RebuiltSkill(Long tenantId, Long Id, Long skillTypeId, Long createdBy) {
        super(tenantId, skillTypeId, createdBy);
        this.id = id;
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    RebuiltSkill resetLevel(SkillLevel level) {
        this.level = level;
        return this;
    }

    RebuiltSkill resetDuration(int duration) {
        this.duration = duration;
        return this;
    }
}
