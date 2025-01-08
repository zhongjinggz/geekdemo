package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.CreateEmpRequest;
import demo.unjuanable.domain.common.validator.CommonOrgValidator;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.*;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpAssembler {

    final EmpNumGenerator empNumGenerator;

    final CommonOrgValidator assertOrg;

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

}
