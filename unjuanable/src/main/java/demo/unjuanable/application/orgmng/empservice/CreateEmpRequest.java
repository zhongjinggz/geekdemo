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

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
