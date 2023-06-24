package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.WorkExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
}
