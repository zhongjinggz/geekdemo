package demo.unjuanable.application.orgmng.empservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseEmpRequest {
    private Long tenantId;
    private String idNum;
    private String name;
    private String genderCode;
    private LocalDate dob;
    protected List<SkillDto> skills = new ArrayList<>();
    private List<WorkExperienceDto> experiences = new ArrayList<>();
    private List<String> postCodes = new ArrayList<>();



    public Long getTenantId() {
        return tenantId;
    }

    public BaseEmpRequest setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public BaseEmpRequest setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseEmpRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public BaseEmpRequest setGenderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public BaseEmpRequest setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public BaseEmpRequest setSkills(List<SkillDto> skills) {
        this.skills = skills;
        return this;
    }

    public BaseEmpRequest addSkill(Long skillTypeId, String levelCode, Integer duration) {
        this.skills.add(new SkillDto(skillTypeId, levelCode, duration));
        return this;
    }

    public List<WorkExperienceDto> getExperiences() {
        return experiences;
    }

    public BaseEmpRequest setExperiences(List<WorkExperienceDto> experiences) {
        this.experiences = experiences;
        return this;
    }

    public BaseEmpRequest setPostCodes(List<String> postCodes) {
        this.postCodes = postCodes;
        return this;
    }

    public List<String> getPostCodes() {
        return postCodes;
    }
}