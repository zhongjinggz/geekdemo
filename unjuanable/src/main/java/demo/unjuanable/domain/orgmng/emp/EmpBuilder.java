package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.domain.common.validator.CommonOrgValidator;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumCounterRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmpBuilder {
    final CommonOrgValidator assertOrg;
    final EmpNumCounterRepository empNumCounterRepository;

    private Long tenantId;
    private Long orgId;
    private String idNum;
    private LocalDate dob;
    private String name;
    private String genderCode;
    private String statusCode;
    private Long createdBy;

    private final List<Map<String, Object>> skills = new ArrayList<>();
    private final List<Map<String, Object>> experiences = new ArrayList<>();
    private final List<String> postCodes = new ArrayList<>();

    EmpBuilder(CommonOrgValidator assertOrg
            , EmpNumCounterRepository empNumCounterRepository) {
        this.assertOrg = assertOrg;
        this.empNumCounterRepository = empNumCounterRepository;
    }

    public EmpBuilder tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public EmpBuilder orgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public EmpBuilder idNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public EmpBuilder dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public EmpBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EmpBuilder genderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public EmpBuilder statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public EmpBuilder createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void validate() {
        assertOrg.shouldValid(tenantId, orgId);
    }

    public String generateEmpNum(Long tenantId) {
        int yearNum = LocalDate.now().getYear();
        int maxNum = empNumCounterRepository.nextNumByYear(tenantId, yearNum);
        return (String.format("%04d%08d", yearNum, maxNum));
    }

    public EmpBuilder addSkill(Long skillTypeId, String levelCode, Integer duration) {
        skills.add(Map.of(
                "skillTypeId", skillTypeId
                , "levelCode", levelCode
                , "duration", duration));
        return this;
    }

    public EmpBuilder addExperience(LocalDate startDate, LocalDate endDate, String company) {
        experiences.add(Map.of(
                "startDate", startDate
                , "endDate", endDate
                , "company", company));
        return this;
    }

    public EmpBuilder addPostCode(String postCode) {
        postCodes.add(postCode);
        return this;
    }

    public Emp build() {

        validate();

        Emp result = new Emp(tenantId, EmpStatus.ofCode(statusCode), createdBy)
                .setEmpNum(generateEmpNum(tenantId))
                .setIdNum(idNum)
                .setDob(dob)
                .setOrgId(orgId)
                .setName(name)
                .setGender(Gender.ofCode(genderCode));


        skills.forEach(s ->
                result.addSkill(
                        (Long) s.get("skillTypeId")
                        , SkillLevel.ofCode((String) s.get("levelCode"))
                        , (Integer) s.get("duration")
                        , createdBy)
        );

        experiences.forEach(e -> result.addExperience(
                Period.of((LocalDate) e.get("startDate"), (LocalDate) e.get("endDate"))
                , (String) e.get("company")
                , createdBy)
        );

        postCodes.forEach((p -> result.addEmpPost(p, createdBy)));

        return result;
    }
}
