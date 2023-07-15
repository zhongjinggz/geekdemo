package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.common.validator.CommonOrgValidator;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.*;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmpAssembler {

    EmpNumGenerator empNumGenerator;

    CommonOrgValidator assertOrg;

    @Autowired
    public EmpAssembler(CommonOrgValidator assertOrg, EmpNumGenerator empNumGenerator) {
        this.assertOrg = assertOrg;
        this.empNumGenerator = empNumGenerator;
    }

    Emp toEmp(CreateEmpRequest request, Long userId) {

        validateCreateRequest(request);

        String empNum = empNumGenerator.generateEmpNum(request.getTenantId());

        Emp result = new Emp(request.getTenantId()
                , EmpStatus.ofCode(request.getStatusCode())
                , userId);
        result.setEmpNum(empNum)
                .setIdNum(request.getIdNum())
                .setDob(request.getDob())
                .setOrgId(request.getOrgId())
                .setName(request.getName())
                .setGender(Gender.ofCode(request.getGenderCode()));


        request.getSkills().forEach(s -> result.addSkill(
                s.getSkillTypeId()
                , SkillLevel.ofCode(s.getLevelCode())
                , s.getDuration()
                , userId));

        request.getExperiences().forEach(e -> result.addExperience(
                Period.of(e.getStartDate(), e.getEndDate())
                , e.getCompany()
                , userId));

        request.getPostCodes().forEach((p -> result.addEmpPost(p, userId)));

        return result;
    }

    void validateCreateRequest(CreateEmpRequest request) {
        assertOrg.shouldValid(
                request.getTenantId(), request.getOrgId());
    }

    EmpResponse toResponse(Emp emp) {
        EmpResponse result = new EmpResponse();

        List<SkillDto> skills = new ArrayList<>();
        emp.getSkills().stream()
                .filter(s -> !s.getChangingStatus().equals(ChangingStatus.DELETED))
                .forEach(s ->
                        skills.add(new SkillDto(s.getId()
                                , s.getSkillTypeId()
                                , s.getLevel().code()
                                , s.getDuration())));

        List<WorkExperienceDto> experiences = new ArrayList<>();
        emp.getExperiences().forEach(e ->
                experiences.add(new WorkExperienceDto(
                         e.getPeriod().getStart()
                        , e.getPeriod().getEnd()
                        , e.getCompany())));

        result.setId(emp.getId())
                .setTenantId(emp.getTenantId())
                .setOrgId(emp.getOrgId())
                .setEmpNum(emp.getEmpNum())
                .setIdNum(emp.getIdNum())
                .setName(emp.getName())
                .setGenderCode(emp.getGender().code())
                .setDob(emp.getDob())
                .setStatusCode(emp.getStatus().code())
                .setSkills(skills)
                .setExperiences(experiences);

        return result;
    }
}
