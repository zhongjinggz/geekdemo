package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmpRepositoryJdbc implements EmpRepository {
    private final EmpDao empDao;
    private final SkillDao skillDao;
    private final WorkExperienceDao workExperienceDao;
    private final EmpPostDao empPostDao;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc
            , EmpDao empDao
            , SkillDao skillDao
            , WorkExperienceDao workExperienceDao
            , EmpPostDao empPostDao) {

        this.empDao = empDao;
        this.skillDao = skillDao;
        this.workExperienceDao = workExperienceDao;
        this.empPostDao = empPostDao;
    }

    @Override
    public boolean save(Emp emp) {
        if (saveEmp(emp)) {
            emp.getSkills().forEach(s -> saveSkill(emp, s));
            emp.getExperiences().forEach(e -> saveWorkExperience(emp, e));
            emp.getEmpPosts().forEach(p -> saveEmpPost(emp, p));
            return true;
        } else {
            return false;
        }
    }

    private boolean saveEmp(Emp emp) {
        switch (emp.getChangingStatus()) {
            case NEW:
                empDao.insert(emp);
                break;
            case UPDATED:
                if (!empDao.update(emp)) {
                    return false;
                }
                break;
        }
        return true;
    }

    private void saveSkill(Emp emp, Skill skill) {
        switch (skill.getChangingStatus()) {
            case NEW:
                skillDao.insert(skill, emp.getId());
                break;
            case UPDATED:
                skillDao.update(skill);
                break;
            case DELETED:
                skillDao.delete(skill);
                break;

        }
    }


    private void saveWorkExperience(Emp emp, WorkExperience e) {
        // TODO update as saveSkill()
        workExperienceDao.insert(e, emp.getId());
    }

    private void saveEmpPost(Emp emp, EmpPost p) {
        // TODO update as saveSkill()
        empPostDao.insert(p, emp.getId());
    }


    @Override
    public boolean existByIdAndStatus(Long tenantId, Long id, EmpStatus... statuses) {
        return !empDao.selectOneByIdAndStatus(tenantId, id, statuses).isEmpty();
    }

    @Override
    public Optional<Emp> findById(Long tenantId, Long id) {
        Optional<RebuiltEmp> empMaybe = empDao.selectById(tenantId, id);
        if (empMaybe.isPresent()) {
            RebuiltEmp emp = empMaybe.get();
            attachSkills(emp);
            attachExperiences(emp);
            attachPosts(emp);
            return Optional.of(emp);
        } else {
            return Optional.empty();
        }
    }

    private void attachSkills(RebuiltEmp emp) {
        List<Map<String, Object>> skills = skillDao.selectByEmpId(emp);

        skills.forEach(skill -> emp.reAddSkill(
                (Long) skill.get("id")
                , (Long) skill.get("skill_type_id")
                , SkillLevel.ofCode((String) skill.get("level_code"))
                , (Integer) skill.get("duration")
                , (Long) skill.get("created_by")
        ));

    }

    private void attachExperiences(RebuiltEmp emp) {
        //TODO
    }

    private void attachPosts(RebuiltEmp emp) {
        //TODO
    }
}
