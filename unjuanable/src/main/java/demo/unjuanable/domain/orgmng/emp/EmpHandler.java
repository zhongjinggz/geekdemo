package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.domain.common.valueobject.Period;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EmpHandler {

// TODO: 增加校验规则
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

    public void addSkill(Emp emp, Long userId, Long skillTypeId, SkillLevel level, Integer duration) {
        emp.addSkill(skillTypeId , level , duration , userId);
    }

    public void deleteSkill(Emp emp, Long skillTypeId) {
        emp.deleteSkill(skillTypeId);
    }

    public void updateSkill(Emp emp, Long userId, Long skillTypeId, SkillLevel level, Integer duration) {
        emp.updateSkill(skillTypeId, level, duration, userId);
    }

    public void addExperience(Emp emp, Period period, String company, Long createdBy) {
        emp.addExperience(period
                , company
                , createdBy);
    }

    public void deleteExperience(Emp emp, Period period) {
        emp.deleteExperience(period);
    }

    public void updateExperience(Emp emp, Period period, String company, Long updatedBy) {
        emp.updateExperience(period
                , company
                , updatedBy);
    }

    public void addEmpPost(Emp emp, String postCode, Long createdBy) {
        emp.addEmpPost(postCode, createdBy);
    }

    public void deleteEmpPost(Emp emp, String postCode) {
        emp.deleteEmpPost(postCode);
    }
}
