package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Skill;

import java.time.LocalDate;
import java.util.List;

public class UpdateEmpRequest extends BaseEmpRequest {
    private String empNum;

    public String getEmpNum() {
        return empNum;
    }

    public UpdateEmpRequest setEmpNum(String empNum) {
        this.empNum = empNum;
        return this;
    }

    public UpdateEmpRequest setName(String name) {
        super.setName(name);
        return this;
    }

    public UpdateEmpRequest setTenantId(Long tenantId) {
        super.setTenantId(tenantId);
        return this;
    }

    public UpdateEmpRequest setIdNum(String idNum) {
        super.setIdNum(idNum);
        return this;
    }

    public UpdateEmpRequest setGenderCode(String genderCode) {
        super.setGenderCode(genderCode);
        return this;
    }

    public UpdateEmpRequest setDob(LocalDate dob) {
        super.setDob(dob);
        return this;
    }

    public UpdateEmpRequest setSkills(List<SkillDto> skills) {
        super.setSkills(skills);
        return this;
    }

    public UpdateEmpRequest addSkill(Long skillTypeId, String levelCode, Integer duration) {
        super.addSkill(skillTypeId, levelCode, duration);
        return this;
    }
    public UpdateEmpRequest addSkill(Long id, Long skillTypeId, String levelCode, Integer duration) {
        super.addSkill(id, skillTypeId, levelCode, duration);
        return this;
    }

    public UpdateEmpRequest setExperiences(List<WorkExperienceDto> experiences) {
        super.setExperiences(experiences);
        return this;
    }

    boolean isSkillAbsent(Skill otherSkill) {
        return skills.stream()
                .noneMatch(skill -> skill.getSkillTypeId().equals(otherSkill.getSkillTypeId()));
    }

    public UpdateEmpRequest removeSkill(long skillTypeId) {
        skills.removeIf(skill -> skill.getSkillTypeId().equals(skillTypeId));
        return this;
    }

    public UpdateEmpRequest updateSkill(long typeId, String levelCode, int duration) {
        skills.stream()
                .filter(skill -> skill.getSkillTypeId().equals(typeId))
                .findFirst()
                .ifPresent(skill -> {
                    skill.setLevelCode(levelCode);
                    skill.setDuration(duration);
                });
        return this;
    }
}
