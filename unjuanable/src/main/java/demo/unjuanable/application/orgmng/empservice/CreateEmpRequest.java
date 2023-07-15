package demo.unjuanable.application.orgmng.empservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateEmpRequest extends BaseEmpRequest {
    private Long orgId;
    private String statusCode;


    public CreateEmpRequest setTenantId(Long tenantId) {
        super.setTenantId(tenantId);
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public CreateEmpRequest setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public CreateEmpRequest setIdNum(String idNum) {
        super.setIdNum(idNum);
        return this;
    }

    public CreateEmpRequest setName(String name) {
        super.setName(name);
        return this;
    }

    public CreateEmpRequest setGenderCode(String genderCode) {
        super.setGenderCode(genderCode);
        return this;
    }

    public CreateEmpRequest setDob(LocalDate dob) {
        super.setDob(dob);
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public CreateEmpRequest setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public CreateEmpRequest setSkills(List<SkillDto> skills) {
        super.setSkills(skills);
        return this;
    }

    public CreateEmpRequest addSkill(Long skillTypeId, String levelCode, Integer duration) {
        super.addSkill(skillTypeId, levelCode, duration);
        return this;
    }

    public CreateEmpRequest setExperiences(List<WorkExperienceDto> experiences) {
        super.setExperiences(experiences);
        return this;
    }

    public CreateEmpRequest addExperience(LocalDate startDate, LocalDate endDate, String company) {
        super.addExperience( startDate, endDate, company);
        return this;
    }

    public CreateEmpRequest setPostCodes(List<String> postCodes) {
        super.setPostCodes(postCodes);
        return this;
    }
}
