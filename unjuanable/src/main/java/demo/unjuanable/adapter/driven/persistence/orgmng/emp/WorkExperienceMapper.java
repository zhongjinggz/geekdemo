package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    public List<WorkExperience> selectByEmpId(Long tenantId, Long empId) {
        String sql = "select id" +
                ", tenant_id" +
                ", emp_id" +
                ", company" +
                ", start_date" +
                ", end_date" +
                ", created_at" +
                ", created_by" +
                ", last_updated_at" +
                ", last_updated_by " +
                "from work_experience " +
                "where emp_id = ? and tenant_id = ?";

        return selectList(sql, mapToWorkExperience(),empId, tenantId);
    }

    private Function<Map<String, Object>, WorkExperience> mapToWorkExperience() {
        return expMap -> {
            return new WorkExperience((Long) expMap.get("tenant_id")
                    , (Long) expMap.get("id")
                    , toLocalDate(expMap, "start_date")
                    , toLocalDate(expMap, "end_date")
                    , (String) expMap.get("company")
                    , (LocalDateTime) expMap.get("created_at")
                    , (Long) expMap.get("created_by")
                    , (LocalDateTime) expMap.get("last_updated_at")
                    , (Long) expMap.get("last_updated_by"));
        };
    }
}
