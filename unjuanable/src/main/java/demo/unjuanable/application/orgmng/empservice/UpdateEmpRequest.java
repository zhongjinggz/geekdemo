package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Skill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateEmpRequest {
    private Long tenantId;
    private String idNum;

    private String name;
    private String genderCode;
    private LocalDate dob;

    private List<SkillDto> skills = new ArrayList<>();
    private List<WorkExperienceDto> experiences = new ArrayList<>();

    public Long getTenantId() {
        return tenantId;
    }

    public UpdateEmpRequest setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public UpdateEmpRequest setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateEmpRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public UpdateEmpRequest setGenderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public UpdateEmpRequest setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }
    public List<SkillDto> getSkills() {
        return skills;
    }

    public UpdateEmpRequest setSkills(List<SkillDto> skills) {
        this.skills = skills;
        return this;
    }

    public UpdateEmpRequest addSkill(Long skillTypeId, String levelCode, Integer duration) {
        this.skills.add(new SkillDto(skillTypeId, levelCode, duration));
        return this;
    }

    public List<WorkExperienceDto> getExperiences() {
        return experiences;
    }

    public UpdateEmpRequest setExperiences(List<WorkExperienceDto> experiences) {
        this.experiences = experiences;
        return this;
    }
    public String getEmpNum() {
        return null;
    }

    boolean isSkillAbsent(Skill otherSkill) {
        return skills.stream()
                .noneMatch(skill -> skill.getSkillTypeId().equals(otherSkill.getSkillTypeId()));
    }
}
