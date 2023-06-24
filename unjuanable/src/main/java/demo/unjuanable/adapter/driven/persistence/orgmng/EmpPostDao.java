package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.EmpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmpPostDao {
    final JdbcTemplate jdbc;
    final SimpleJdbcInsert empPostInsert;

    @Autowired
    public EmpPostDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.empPostInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("emp_post");
    }
    void insert(EmpPost empPost, Long empId) {
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", empId);
        params.put("post_code", empPost.getPostCode());
        params.put("created_at", empPost.getCreatedAt());
        params.put("created_by", empPost.getCreatedBy());

        empPostInsert.execute(params);
    }
}
