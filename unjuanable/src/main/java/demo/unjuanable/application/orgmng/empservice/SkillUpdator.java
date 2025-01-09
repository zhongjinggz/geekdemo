package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.SkillDto;
import demo.unjuanable.common.framework.application.CollectionUpdator;
import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpHandler;
import demo.unjuanable.domain.orgmng.emp.Skill;
import demo.unjuanable.domain.orgmng.emp.SkillLevel;
import org.springframework.stereotype.Component;

@Component
public class SkillUpdator extends CollectionUpdator<Emp, Skill, SkillDto> {
    private final EmpHandler handler;

    SkillUpdator(EmpHandler handler) {
        this.handler = handler;
    }


    @Override
    protected boolean isSame(Skill currentItem, SkillDto requestItem) {
        return currentItem.getSkillTypeId().equals(requestItem.getSkillTypeId());
    }

    @Override
    protected void updateItem(Emp emp, SkillDto requestItem, Long userId) {
        handler.updateSkill(emp
                , userId
                , requestItem.getSkillTypeId()
                , SkillLevel.ofCode(requestItem.getLevelCode())
                , requestItem.getDuration());
    }

    @Override
    protected void removeItem(Emp emp, Skill currentItem) {
        handler.deleteSkill(emp, currentItem.getSkillTypeId());
    }

    @Override
    protected void addItem(Emp emp, SkillDto requestItem, Long userId) {
        handler.addSkill(emp
                , userId
                , requestItem.getSkillTypeId()
                , SkillLevel.ofCode(requestItem.getLevelCode())
                , requestItem.getDuration());
    }
}