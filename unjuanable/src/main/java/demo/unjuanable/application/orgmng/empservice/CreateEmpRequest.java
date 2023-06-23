package demo.unjuanable.application.orgmng.empservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateEmpRequest {
    private Long tenantId;
    private Long orgId;
    private String idNum;

    private String name;
    private String genderCode;
    private LocalDate dob;

    private String statusCode;

    private List<SkillDto> skills = new ArrayList<>();
    private List<WorkExperienceDto> experiences = new ArrayList<>();
    private List<String> postCodes = new ArrayList<>();

    public Long getTenantId() {
        return tenantId;
    }

    public CreateEmpRequest setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public CreateEmpRequest setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public CreateEmpRequest setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateEmpRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public CreateEmpRequest setGenderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }


    public LocalDate getDob() {
        return dob;
    }

    public CreateEmpRequest setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public CreateEmpRequest setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public CreateEmpRequest addSkill(Long skillTypeId, String levelCode, Integer duration) {
        this.skills.add(new SkillDto(skillTypeId, levelCode, duration));
        return this;
    }

    public List<WorkExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<WorkExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<String> getPostCodes() {
        return postCodes;
    }

    public void setPostCodes(List<String> postCodes) {
        this.postCodes = postCodes;
    }
}
