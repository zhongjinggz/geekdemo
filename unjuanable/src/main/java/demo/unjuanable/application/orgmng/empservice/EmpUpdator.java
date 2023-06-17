package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EmpUpdator {
    public void update(Emp emp, UpdateEmpRequest request, Long userId) {

        emp.setIdNum(request.getIdNum())
                .setDob(request.getDob())
                .setGender(Gender.ofCode(request.getGenderCode()))
                .setLastUpdatedAt(LocalDateTime.now())
                .setLastUpdatedBy(userId)
                .toUpdate();

        updateSkills(emp, request, userId);
        updateExperiences(emp, request, userId);
    }

    private void updateSkills(Emp emp, UpdateEmpRequest request, Long userId) {
        deleteAbsentSkills(emp, request);
        operatePresentSkills(emp, request, userId);
    }

    private void updateExperiences(Emp emp, UpdateEmpRequest request, Long userId) {
        //TODO
    }

    private void deleteAbsentSkills(Emp emp, UpdateEmpRequest request) {
        emp.getSkills().forEach(presentSkill -> {
            if (request.isSkillAbsent(presentSkill)) {
                emp.deleteSkill(presentSkill.getSkillTypeId());
            }
        });
    }

    private void operatePresentSkills(Emp emp, UpdateEmpRequest request, Long userId) {
        for (SkillDto skill : request.getSkills()) {
            Optional<Skill> skillMaybe = emp.getSkill(skill.getSkillTypeId());
            if (skillMaybe.isPresent()) {
                emp.updateSkill(skill.getSkillTypeId()
                        , SkillLevel.ofCode(skill.getLevelCode())
                        , skill.getDuration()
                        , userId);
            } else {
                emp.addSkill(skill.getSkillTypeId()
                        , SkillLevel.ofCode(skill.getLevelCode())
                        , skill.getDuration()
                        , userId);
            }
        }

    }

}
