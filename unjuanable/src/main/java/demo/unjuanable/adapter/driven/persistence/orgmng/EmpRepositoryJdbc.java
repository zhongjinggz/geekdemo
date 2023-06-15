package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;


@Repository
public class EmpRepositoryJdbc implements EmpRepository {

    final JdbcTemplate jdbc;
    final SimpleJdbcInsert empInsert;
    final SimpleJdbcInsert skillInsert;
    final SimpleJdbcInsert insertWorkExperience;
    final SimpleJdbcInsert empPostInsert;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.empInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("emp")
                .usingGeneratedKeyColumns("id");

        this.skillInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("skill")
                .usingGeneratedKeyColumns("id");

        this.insertWorkExperience = new SimpleJdbcInsert(jdbc)
                .withTableName("work_experience")
                .usingGeneratedKeyColumns("id");

        this.empPostInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("emp_post");
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
                insertEmpRecord(emp);
                break;
            case UPDATED:
                if (!updateEmpRecord(emp)) {
                    return false;
                }
                break;
        }
        return true;
    }

    private void insertEmpRecord(Emp emp) {
        Map<String, Object> parms = Map.of(
                "tenant_id", emp.getTenantId()
                , "org_id", emp.getOrgId()
                , "num", emp.getNum()
                , "id_num", emp.getIdNum()
                , "name", emp.getName()
                , "gender", emp.getGender().code()
                , "dob", emp.getDob()
                , "status", emp.getStatus().code()
                , "created_at", emp.getCreatedAt()
                , "created_by", emp.getCreatedBy()
        );

        Number createdId = empInsert.executeAndReturnKey(parms);

        forceSet(emp, "id", createdId.longValue());
    }

    private boolean updateEmpRecord(Emp emp) {
        String sql = "update emp " +
                " set version = version + 1 " +
                ", org_id = ?" +
                ", emp_num = ?" +
                ", id_num =? " +
                ", name = ?" +
                ", gender_code =?" +
                ", dob = ?" +
                ", status_code =?" +
                ", last_updated_at =?" +
                ", last_updated_by =? " +
                " where tenant_id = ? and id = ? and version = ?";
        int affected = this.jdbc.update(sql
                , emp.getOrgId()
                , emp.getNum()
                , emp.getIdNum()
                , emp.getName()
                , emp.getGender().code()
                , emp.getDob()
                , emp.getStatus()
                , emp.getLastUpdatedAt()
                , emp.getLastUpdatedBy()
                , emp.getTenantId()
                , emp.getId()
                , emp.getVersion());

        return affected == 1;
    }

    private void saveSkill(Emp emp, Skill skill) {
        switch (skill.getChangingStatus()) {
            case NEW:
                insertSkillRecord(skill, emp.getId());
                break;
            case UPDATED:
                updateSkillRecord(skill);
                break;
            case DELETED:
                deleteSkillRecord(skill);
                break;

        }
    }

    private void insertSkillRecord(Skill skill, Long empId) {
        Map<String, Object> parms = Map.of(
                "emp_id", empId,
                "tenant_id", skill.getTenantId(),
                "skill_type_id", skill.getSkillTypeId(),
                "level_code", skill.getLevel().code(),
                "duration", skill.getDuration(),
                "created_at", skill.getCreatedAt(),
                "created_by", skill.getCreatedBy()
        );

        Number createdId = skillInsert.executeAndReturnKey(parms);

        forceSet(skill, "id", createdId.longValue());
    }

    private void updateSkillRecord(Skill skill) {
        String sql = "update skill "
                + " set level_code = ?"
                + ", duration = ?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ?"
                + " where tenant_id = ? and id = ? ";

        this.jdbc.update(sql
                , skill.getSkillTypeId()
                , skill.getDuration()
                , skill.getLastUpdatedAt()
                , skill.getLastUpdatedBy()
                , skill.getTenantId()
                , skill.getId());
    }


    private void deleteSkillRecord(Skill skill) {
        this.jdbc.update("delete from skll where tenant_id = ? and id = ?"
                , skill.getTenantId()
                , skill.getId());
    }

    private void saveWorkExperience(Emp emp, WorkExperience e) {
        // TODO update as saveSkill()
        insertWorkExperienceRecord(e, emp.getId());
    }

    private void insertWorkExperienceRecord(WorkExperience experience, Long empId) {
        Map<String, Object> parms = new HashMap<>();

        parms.put("emp_id", empId);
        parms.put("tenant_id", experience.getTenantId());
        parms.put("start_date", experience.getPeriod().getStart());
        parms.put("end_date", experience.getPeriod().getEnd());
        parms.put("company", experience.getCompany());
        parms.put("created_at", experience.getCreatedAt());
        parms.put("created_by", experience.getCreatedBy());

        Number createdId = insertWorkExperience.executeAndReturnKey(parms);

        forceSet(experience, "id", createdId.longValue());
    }

    private void saveEmpPost(Emp emp, EmpPost p) {
        // TODO update as saveSkill()
        insertEmpPostRecord(p, emp.getId());
    }

    private void insertEmpPostRecord(EmpPost empPost, Long empId) {
        Map<String, Object> parms = Map.of("emp_id", empId
                , "post_code", empPost.getPostCode()
                , "created_at", empPost.getCreatedAt()
                , "created_by", empPost.getCreatedBy());

        empPostInsert.execute(parms);
    }

    @Override
    public int countByIdAndStatus(long tenantId, long id, EmpStatus... statuses) {
        final String sql = "select count(*) from emp " +
                "where tenant_id = ? " +
                "and id = ? ";

        if (statuses.length > 0) {
            StringBuilder orSql = new StringBuilder(sql).append("and (status = '' ");
            for (EmpStatus status : statuses) {
                orSql.append("or status = '").append(status.code()).append("' ");
            }
            orSql.append(")");
        }

        return jdbc.queryForObject(sql, Integer.class, tenantId, id);
    }

    @Override
    public boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses) {
        return countByIdAndStatus(tenant, id, statuses) > 0;
    }

    @Override
    public Optional<Emp> findById(Long tenantId, Long id) {
        Optional<RebuiltEmp> empMaybe = retrieveEmp(tenantId, id);
        if (empMaybe.isPresent()) {
            RebuiltEmp emp = empMaybe.get();
            retrieveSkills(emp);
            retrieveExperiences(emp);
            retrievePosts(emp);
            return Optional.of(emp);
        } else {
            return Optional.empty();
        }
    }

    private Optional<RebuiltEmp> retrieveEmp(Long tenantId, Long id) {
        String sql = " select version, org_id, emp_num, id_num, name, gender_code, dob, status_code "
                + " from emp "
                + " where id = ? and tenant_id = ? ";

        RebuiltEmp emp = jdbc.queryForObject(sql,
                (rs, rowNum) -> {

                    RebuiltEmp newEmp = new RebuiltEmp(tenantId
                            , id
                            , rs.getTimestamp("create_at").toLocalDateTime()
                            , rs.getLong("created_by"));

                    newEmp.resetVersion(rs.getLong("version"))
                            .resetOrgId(rs.getLong("org_id"))
                            .resetNum(rs.getString("num"))
                            .resetIdNum(rs.getString("id_num"))
                            .resetName(rs.getString("name"))
                            .resetGender(Gender.ofCode(rs.getString("gender_code")))
                            .resetDob(rs.getDate("dob").toLocalDate())
                            .resetStatus(EmpStatus.ofCode(rs.getString("status_code")));
                    return newEmp;
                },
                id, tenantId);

        return Optional.ofNullable(emp);
    }

    private void retrieveSkills(RebuiltEmp emp) {
        String sql = " select id, tenant_id, skill_type_id, level, duration "
                + " from skill "
                + " where tenant_id = ? and emp_id = ? ";

        List<Map<String, Object>> skills = jdbc.queryForList(sql, emp.getTenantId(), emp.getId());

        skills.forEach(skill -> emp.reAddSkill(
                (Long) skill.get("id")
                , (Long) skill.get("skill_type_id")
                , SkillLevel.ofCode((String) skill.get("level_code"))
                , (Integer) skill.get("duration")
                , (Long) skill.get("created_by")
        ));

    }

    private void retrieveExperiences(RebuiltEmp emp) {
        //TODO
    }

    private void retrievePosts(RebuiltEmp emp) {
        //TODO
    }
}
