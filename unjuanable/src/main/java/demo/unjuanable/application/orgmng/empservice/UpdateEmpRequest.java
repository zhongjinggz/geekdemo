package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Skill;

import java.time.LocalDate;
import java.util.List;

public class UpdateEmpRequest {
    private Long tenantId;
    private String num;
    private String idNum;

    private String name;
    private String genderCode;
    private LocalDate dob;

    private List<SkillDto> skills;
    private List<WorkExperienceDto> experiences;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public List<WorkExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<WorkExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public String getNum() {
        return null;
    }

    public void setNum(String num) {
        this.num = num;
    }

    boolean isSkillAbsent(Skill otherSkill) {
        return skills.stream()
                .noneMatch(skill -> skill.getSkillTypeId().equals(otherSkill.getSkillTypeId()));
    }
}
