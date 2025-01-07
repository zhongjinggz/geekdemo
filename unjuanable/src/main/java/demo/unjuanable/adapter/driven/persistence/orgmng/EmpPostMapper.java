package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.orgmng.emp.EmpPost;
import demo.unjuanable.domain.orgmng.emp.RebuiltEmp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    void attachTo(RebuiltEmp emp) {
        //TODO
    }
}
