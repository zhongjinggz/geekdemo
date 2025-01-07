package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.adapter.driven.persistence.orgmng.RebuiltWorkExperience;
import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.common.valueobject.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class RebuiltEmp extends Emp {
    public RebuiltEmp(Long tenantId, Long id, LocalDateTime create_at, long created_by) {
        super(tenantId, id, create_at, created_by);
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    public RebuiltEmp resetVersion(Long version) {
        this.version = version;
        return this;
    }

    public RebuiltEmp resetOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public RebuiltEmp resetEmpNum(String num) {
        this.empNum = num;
        return this;
    }

    public RebuiltEmp resetIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public RebuiltEmp resetName(String name) {
        this.name = name;
        return this;
    }

    public RebuiltEmp resetGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public RebuiltEmp resetDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public RebuiltEmp resetStatus(EmpStatus status) {
        this.status = status;
        return this;
    }

    public RebuiltEmp reAddSkill(Long id, Long skillTypeId, SkillLevel level, Integer duration, Long createdBy) {

        RebuiltSkill newSkill = new RebuiltSkill(this, tenantId, id, skillTypeId, createdBy)
                .resetLevel(level)
                .resetDuration(duration);

        skills.put(skillTypeId, newSkill);
        return this;
    }

    public RebuiltEmp reUpdateSkill(Long skillTypeId, SkillLevel level, Integer duration, Long userId) {
        this.getSkill(skillTypeId)
                .orElseThrow(() -> new IllegalArgumentException("不存在要修改的skillTypeId!")).setLevel(level)
                .setDuration(duration).setLastUpdatedBy(userId)
                .setLastUpdatedBy(userId)
                .setLastUpdatedAt(LocalDateTime.now());
        return this;
    }

    public RebuiltEmp reAddExperience(Long experienceId, Period period, String company, Long userId) {
        RebuiltWorkExperience newExperience = new RebuiltWorkExperience(
                this
                , tenantId
                , experienceId
                , period
                , LocalDateTime.now()
                , userId)
                .resetCompany(company);
        experiences.put(period, newExperience);
        return this;

    }

    public RebuiltEmp reUpdateExperience(Period period, String company, Long userId) {
        this.getExperience(period)
                .orElseThrow(() -> new IllegalArgumentException("不存在要修改的experience!"))
                .setCompany(company)
                .setLastUpdatedBy(userId)
                .setLastUpdatedAt(LocalDateTime.now());
        return this;
    }

    public RebuiltEmp reAddEmpPost(String postCode, Long userId) {
        //TODO
        return this;
    }

    public RebuiltEmp resetSkills(Collection<Skill> skills) {
        for (Skill skill : skills) {
            this.skills.put(skill.getSkillTypeId(), skill);
        }
        return this;
    }

    public RebuiltEmp deleteSkillCompletely(Long skillTypeId) {
        this.skills.remove(skillTypeId);
        return this;
    }

    public RebuiltEmp deleteExperienceCompletely(Period period) {
        this.experiences.remove(period);
        return this;
    }
}
