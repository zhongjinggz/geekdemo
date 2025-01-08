package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.application.orgmng.empservice.dto.CreateEmpRequest;
import demo.unjuanable.domain.common.validator.CommonOrgValidator;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumCounterRepository;

import java.time.LocalDate;

public class EmpBuilder {
    final CommonOrgValidator assertOrg;
    final EmpNumCounterRepository empNumCounterRepository;

    private Long tenantId;
    private Long createdBy;
    private String name;

    EmpBuilder(CommonOrgValidator assertOrg
    , EmpNumCounterRepository empNumCounterRepository) {
        this.assertOrg = assertOrg;
        this.empNumCounterRepository = empNumCounterRepository;
    }

    public EmpBuilder tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public EmpBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EmpBuilder createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Emp build() {
        validate();

        return null;
    }

    private void validate() {
    }


    public void validateCreateRequest(Long tenantId, Long orgId) {
        assertOrg.shouldValid(
                tenantId, orgId);
    }

    public String generateEmpNum(Long tenantId) {
        int yearNum = LocalDate.now().getYear();
        int maxNum = empNumCounterRepository.increaseMaxNumByYear(tenantId, yearNum);
        return (String.format("%04d%08d", yearNum, maxNum));
    }

    public Emp build(CreateEmpRequest request, Long userId) {

        validateCreateRequest(request.getTenantId(), request.getOrgId());

        String empNum = generateEmpNum(request.getTenantId());

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
}
