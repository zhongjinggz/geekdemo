package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpPost;
import demo.unjuanable.domain.orgmng.emp.RebuiltEmp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmpPostMapper extends BaseMapper<EmpPost, Emp> {
    final JdbcTemplate jdbc;
    final SimpleJdbcInsert empPostInsert;

    public EmpPostMapper(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.empPostInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("emp_post");
    }

    @Override
    protected void insert(EmpPost empPost, Emp emp) {
        Long empId = emp.getId();
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", empId);
        params.put("post_code", empPost.getPostCode());
        params.put("created_at", empPost.getCreatedAt());
        params.put("created_by", empPost.getCreatedBy());

        empPostInsert.execute(params);
    }

    void attachTo(RebuiltEmp emp) {
        //TODO
    }
}
