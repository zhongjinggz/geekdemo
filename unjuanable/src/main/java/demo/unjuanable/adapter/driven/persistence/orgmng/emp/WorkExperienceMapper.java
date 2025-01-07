package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.RebuiltEmp;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;
import static demo.unjuanable.common.util.SqlUtil.toLocalDate;

@Component
public class WorkExperienceMapper extends Mapper<WorkExperience> {
    public WorkExperienceMapper(JdbcTemplate jdbc) {
        super(jdbc, "work_experience", "id");
    }

    @Override
    protected void delete(WorkExperience workExperience) {
        String sql = "delete from work_experience where tenant_id = ? and id = ?";
        jdbc.update(sql, workExperience.getTenantId(), workExperience.getId());
    }

    @Override
    protected void update(WorkExperience workExperience) {
        String sql = "update work_experience "
                + "set company = ?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ?"
                + " where tenant_id = ? and id = ? ";

        jdbc.update(sql
                , workExperience.getCompany()
                , workExperience.getLastUpdatedAt()
                , workExperience.getLastUpdatedBy()
                , workExperience.getTenantId()
                , workExperience.getId());
    }

    @Override
    protected void insert(WorkExperience workExperience) {
        Map<String, Object> params = new HashMap<>();

        params.put("emp_id", workExperience.getEmpId());
        params.put("tenant_id", workExperience.getTenantId());
        params.put("start_date", workExperience.getPeriod().getStart());
        params.put("end_date", workExperience.getPeriod().getEnd());
        params.put("company", workExperience.getCompany());
        params.put("created_at", workExperience.getCreatedAt());
        params.put("created_by", workExperience.getCreatedBy());

        Number createdId = jdbcInsert.executeAndReturnKey(params);

        forceSet(workExperience, "id", createdId.longValue());
    }

    void attachTo(RebuiltEmp emp) {
        String sql = "select id, start_date, end_date, company " +
                "from work_experience where emp_id = ? and tenant_id = ?";

        List<Map<String, Object>> experiences = jdbc.queryForList(sql, emp.getId(), emp.getTenantId());
        experiences.forEach(experience -> emp.reAddExperience(
                (Long) experience.get("id")
                , Period.of(toLocalDate(experience, "start_date"), toLocalDate(experience, "end_date"))
                , (String) experience.get("company")
                , (Long) experience.get("created_by")
        ));
    }
}
