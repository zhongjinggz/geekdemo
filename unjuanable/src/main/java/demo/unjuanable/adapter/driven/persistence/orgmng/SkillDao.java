package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.RebuiltEmp;
import demo.unjuanable.domain.orgmng.emp.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Component
public class SkillDao {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert skillInsert;

    @Autowired
    public SkillDao(JdbcTemplate jdbc) {

        this.jdbc = jdbc;

        this.skillInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("skill")
                .usingGeneratedKeyColumns("id");

    }

    void insert(Skill skill, Long empId) {

        Map<String, Object> parms = new HashMap<>();
        parms.put("emp_id", empId);
        parms.put("tenant_id", skill.getTenantId());
        parms.put("skill_type_id", skill.getSkillTypeId());
        parms.put("level_code", skill.getLevel().code());
        parms.put("duration", skill.getDuration());
        parms.put("created_at", skill.getCreatedAt());
        parms.put("created_by", skill.getCreatedBy());

        Number createdId = skillInsert.executeAndReturnKey(parms);

        forceSet(skill, "id", createdId.longValue());
    }

    void update(Skill skill) {
        String sql = "update skill "
                + " set level_code = ?"
                + ", duration = ?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ?"
                + " where tenant_id = ? and id = ? ";

        jdbc.update(sql
                , skill.getLevel().code()
                , skill.getDuration()
                , skill.getLastUpdatedAt()
                , skill.getLastUpdatedBy()
                , skill.getTenantId()
                , skill.getId());
    }

    void delete(Skill skill) {
        jdbc.update("delete from skill where tenant_id = ? and id = ?"
                , skill.getTenantId()
                , skill.getId());
    }

    List<Map<String, Object>> selectByEmpId(RebuiltEmp emp) {
        String sql = " select id, tenant_id, skill_type_id, level_code, duration "
                + " from skill "
                + " where tenant_id = ? and emp_id = ? ";

        return jdbc.queryForList(sql, emp.getTenantId(), emp.getId());
    }
}
