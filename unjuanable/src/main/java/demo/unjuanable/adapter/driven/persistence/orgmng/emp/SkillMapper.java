package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.orgmng.emp.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Component
public class SkillMapper extends Mapper<Skill> {

    public SkillMapper(JdbcTemplate jdbc) {
        super(jdbc, "skill", "id");
    }

    @Override
    protected void delete(Skill skill) {
        jdbc.update("delete from skill where tenant_id = ? and id = ?"
                , skill.getTenantId()
                , skill.getId());
    }

    @Override
    protected void update(Skill skill) {
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

    @Override
    protected void insert(Skill skill) {
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", skill.getEmpId());
        params.put("tenant_id", skill.getTenantId());
        params.put("skill_type_id", skill.getSkillTypeId());
        params.put("level_code", skill.getLevel().code());
        params.put("duration", skill.getDuration());
        params.put("created_at", skill.getCreatedAt());
        params.put("created_by", skill.getCreatedBy());

        Number createdId = jdbcInsert.executeAndReturnKey(params);

        forceSet(skill, "id", createdId.longValue());
    }

    List<Skill> selectByEmpId(Long tenantId, Long empId) {
        String sql = " select id" +
                ", tenant_id" +
                ", skill_type_id" +
                ", emp_id" +
                ", level_code" +
                ", duration " +
                ", created_at" +
                ", created_by" +
                ", last_updated_at" +
                ", last_updated_by" +
                " from skill " +
                " where tenant_id = ? and emp_id = ? ";

        return selectList(sql, mapToSkill(), tenantId, empId);
    }

    private Function<Map<String, Object>, Skill> mapToSkill() {
        return skillMap -> {
            return new Skill((Long) skillMap.get("id")
                    , (Long) skillMap.get("tenant_id")
                    , (Long) skillMap.get("skill_type_id")
                    , (String) skillMap.get("level_code")
                    , (Integer) skillMap.get("duration")
                    , (Long) skillMap.get("created_by")
                    , (LocalDateTime) skillMap.get("created_at")
                    , (Long) skillMap.get("last_updated_by")
                    , (LocalDateTime) skillMap.get("last_updated_at")
            );
        };
    }
}
