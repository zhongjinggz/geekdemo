package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.CreateEmpRequest;
import demo.unjuanable.application.orgmng.empservice.dto.EmpResponse;
import demo.unjuanable.application.orgmng.empservice.dto.UpdateEmpRequest;
import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpService {
    private final EmpRepository empRepository;
    private final EmpBuilderFactory builderFactory;
    private final EmpHandler handler;
    private final SkillUpdator skillUpdator;
    private final WorkExperienceUpdator experienceUpdator;
    private final EmpPostUpdator empPostUpdator;


    @Autowired
    public EmpService(EmpRepository empRepository
            , EmpBuilderFactory builderFactory
            , EmpHandler handler
            , SkillUpdator skillUpdator
            , WorkExperienceUpdator experienceUpdator
            , EmpPostUpdator empPostUpdator
    ) {
        this.empRepository = empRepository;
        this.builderFactory = builderFactory;
        this.handler = handler;
        this.skillUpdator = skillUpdator;
        this.experienceUpdator = experienceUpdator;
        this.empPostUpdator = empPostUpdator;
    }

    @Transactional
    public EmpResponse addEmp(CreateEmpRequest request, Long userId) {
        EmpBuilder builder = builderFactory.builder()
                .tenantId(request.getTenantId())
                .orgId(request.getOrgId())
                .idNum(request.getIdNum())
                .dob(request.getDob())
                .name(request.getName())
                .genderCode(request.getGenderCode())
                .statusCode(request.getStatusCode())
                .createdBy(userId);

        request.getSkills().forEach(
                s -> builder.addSkill(
                        s.getSkillTypeId(),
                        s.getLevelCode(),
                        s.getDuration()));
        request.getExperiences().forEach(
                e -> builder.addExperience(
                        e.getStartDate(),
                        e.getEndDate(),
                        e.getCompany())
        );
        request.getPostCodes().forEach(builder::addPostCode);

        Emp emp = builder.build();

        empRepository.save(emp);
        return new EmpResponse(emp);
    }

    @Transactional
    public EmpResponse updateEmp(Long empId, UpdateEmpRequest request, Long userId) {
        Emp emp = empRepository.findById(request.getTenantId(), empId)
                .orElseThrow(() -> new BusinessException(
                        "Emp id(" + empId + ") 不正确！"));

        handler.updateRoot(emp
                , userId
                , request.getIdNum()
                , request.getName()
                , request.getDob()
                , Gender.ofCode(request.getGenderCode()));

        skillUpdator.update(emp, emp.getSkills(), request.getSkills(), userId);
        experienceUpdator.update(emp, emp.getExperiences(), request.getExperiences(), userId);
        empPostUpdator.update(emp, emp.getEmpPosts(), request.getPostCodes(), userId);

        empRepository.save(emp);
        return new EmpResponse(emp);
    }

}
