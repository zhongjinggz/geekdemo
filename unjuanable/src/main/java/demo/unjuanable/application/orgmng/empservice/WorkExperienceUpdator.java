package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.WorkExperienceDto;
import demo.unjuanable.common.framework.application.CollectionUpdator;
import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpHandler;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceUpdator extends CollectionUpdator<Emp, WorkExperience, WorkExperienceDto> {
    private final EmpHandler handler;

    WorkExperienceUpdator(EmpHandler handler) {
        this.handler = handler;
    }

    @Override
    protected boolean isSame(WorkExperience experience, WorkExperienceDto dto) {
        return experience.getPeriod().equals(dto.getPeriod());
    }

    @Override
    protected void updateItem(Emp emp, WorkExperienceDto dto, Long createdBy) {
        handler.updateExperience(emp, dto.getPeriod(), dto.getCompany(), createdBy);
    }

    @Override
    protected void removeItem(Emp emp, WorkExperience dto) {
        handler.deleteExperience(emp, dto.getPeriod());
    }

    @Override
    protected void addItem(Emp emp, WorkExperienceDto dto, Long userId) {
        handler.addExperience(emp, dto.getPeriod(), dto.getCompany(), userId);
    }
}