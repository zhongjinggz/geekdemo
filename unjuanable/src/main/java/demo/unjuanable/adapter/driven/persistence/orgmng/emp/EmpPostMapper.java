package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.orgmng.emp.EmpPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class EmpPostMapper extends Mapper<EmpPost> {

    public EmpPostMapper(JdbcTemplate jdbc) {
        super(jdbc, "emp_post");
    }

    @Override
    protected void insert(EmpPost empPost) {
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", empPost.getEmpId());
        params.put("post_code", empPost.getPostCode());
        params.put("created_at", empPost.getCreatedAt());
        params.put("created_by", empPost.getCreatedBy());

        jdbcInsert.execute(params);
    }

    List<EmpPost> selectByEmpId(Long tenantId, Long empId) {
        String sql = "select emp_id" +
                ", post_code" +
                ", tenant_id" +
                ", created_at" +
                ", created_by" +
                ", last_updated_at" +
                ", last_updated_by " +
                "from emp_post " +
                "where emp_id = ? and tenant_id = ?";
        return selectList(sql, mapToPost(), empId, tenantId);
    }


    private Function<Map<String, Object>, EmpPost> mapToPost() {
        return postMap -> {
            return new EmpPost((String) postMap.get("post_code")
                    , (LocalDateTime) postMap.get("created_at")
                    , (Long) postMap.get("created_by")
                    , (LocalDateTime) postMap.get("last_updated_at")
                    , (Long) postMap.get("last_updated_by"));
        };
    }
}
