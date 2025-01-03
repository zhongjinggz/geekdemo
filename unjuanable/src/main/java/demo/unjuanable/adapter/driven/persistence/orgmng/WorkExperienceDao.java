package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.RebuiltEmp;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Component
public class WorkExperienceDao {

    final JdbcTemplate jdbc;
    final SimpleJdbcInsert insertWorkExperience;

    @Autowired
    public WorkExperienceDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insertWorkExperience = new SimpleJdbcInsert(jdbc)
                .withTableName("work_experience")
                .usingGeneratedKeyColumns("id");
    }

    void insert(WorkExperience experience, Long empId) {
        Map<String, Object> params = new HashMap<>();

        params.put("emp_id", empId);
        params.put("tenant_id", experience.getTenantId());
        params.put("start_date", experience.getPeriod().getStart());
        params.put("end_date", experience.getPeriod().getEnd());
        params.put("company", experience.getCompany());
        params.put("created_at", experience.getCreatedAt());
        params.put("created_by", experience.getCreatedBy());

        Number createdId = insertWorkExperience.executeAndReturnKey(params);

        forceSet(experience, "id", createdId.longValue());
    }

    void update(WorkExperience experience) {
        String sql = "update work_experience "
                + "set company = ?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ?"
                + " where tenant_id = ? and id = ? ";

        jdbc.update(sql
                , experience.getCompany()
                , experience.getLastUpdatedAt()
                , experience.getLastUpdatedBy()
                , experience.getTenantId()
                , experience.getId());
    }

    void delete(WorkExperience experience) {
        String sql = "delete from work_experience where tenant_id = ? and id = ?";
        jdbc.update(sql, experience.getTenantId(), experience.getId());
    }

    public List<Map<String, Object>> selectByEmpId(RebuiltEmp emp) {
        String sql = "select id, start_date, end_date, company " +
                "from work_experience where emp_id = ? and tenant_id = ?";

        return jdbc.queryForList(sql, emp.getId(), emp.getTenantId());
    }
}
