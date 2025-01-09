package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.UpdateEmpRequest;
import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.stereotype.Component;

@Component
public class EmpUpdator {

    private final EmpHandler handler;
    private final SkillUpdator skillUpdator;
    private final WorkExperienceUpdator workExperienceUpdator;
    private final EmpPostUpdator empPostUpdator;

    public EmpUpdator(EmpHandler handler
            , SkillUpdator skillUpdator
            , WorkExperienceUpdator workExperienceUpdator
            , EmpPostUpdator empPostUpdator) {
        this.handler = handler;
        this.skillUpdator = skillUpdator;
        this.workExperienceUpdator = workExperienceUpdator;
        this.empPostUpdator = empPostUpdator;
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
        empPostUpdator.update(emp, emp.getEmpPosts(), request.getPostCodes(), userId);

    }

}