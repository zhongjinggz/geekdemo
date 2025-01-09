package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.UpdateEmpRequest;
import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.stereotype.Component;

@Component
public class EmpUpdator {

    private final EmpHandler handler;
    private final SkillUpdator skillUpdator;
    private final WorkExperienceUpdator workExperienceUpdator;

    public EmpUpdator(EmpHandler handler
            , SkillUpdator skillUpdator
            , WorkExperienceUpdator workExperienceUpdator) {
        this.handler = handler;
        this.skillUpdator = skillUpdator;
        this.workExperienceUpdator = workExperienceUpdator;
    }

    public void update(Emp emp, UpdateEmpRequest request, Long userId) {

        handler.updateRoot(emp
                , userId
                , request.getIdNum()
                , request.getName()
                , request.getDob()
                , Gender.ofCode(request.getGenderCode()));

        skillUpdator.update(emp, emp.getSkills(), request.getSkills(), userId);
        workExperienceUpdator.update(emp, emp.getExperiences(), request.getExperiences(), userId);
    }

}
