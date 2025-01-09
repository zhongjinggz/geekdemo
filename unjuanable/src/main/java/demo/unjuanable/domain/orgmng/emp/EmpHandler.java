package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.domain.common.valueobject.Period;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EmpHandler {

    public EmpHandler() {
    }

    public void updateRoot(Emp emp
            , Long userId
            , String idNum
            , String name
            , LocalDate dob
            , Gender gender) {

        emp.setIdNum(idNum)
                .setName(name)
                .setDob(dob)
                .setGender(gender)
                .setLastUpdatedAt(LocalDateTime.now())
                .setLastUpdatedBy(userId)
                .toUpdate();
    }

    private void updateAuditInfo(Emp emp, Long userId) {
        emp.setLastUpdatedBy(userId);
        emp.setLastUpdatedAt(LocalDateTime.now());
    }

    public void deleteSkill(Emp emp, Long skillTypeId) {
        emp.deleteSkill(skillTypeId);
    }

    public void updateSkill(Emp emp, Long userId, Long skillTypeId, SkillLevel level, Integer duration) {
        emp.updateSkill(skillTypeId, level, duration, userId);
    }

    public void addSkill(Emp emp, Long userId, Long skillTypeId, SkillLevel level, Integer duration) {
        emp.addSkill(skillTypeId , level , duration , userId);
    }

    public void updateExperience(Emp emp, Period period, String company, Long updatedBy) {
        emp.updateExperience(period
                , company
                , updatedBy);
    }

    public void addExperience(Emp emp, Period period, String company, Long createdBy) {
        emp.addExperience(period
                , company
                , createdBy);
    }

    public void deleteExperience(Emp emp, Period period) {
        emp.deleteExperience(period);
    }
}
