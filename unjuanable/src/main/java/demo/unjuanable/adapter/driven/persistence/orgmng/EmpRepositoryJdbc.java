package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpRepositoryJdbc implements EmpRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean existsByIdAndStatus(Long tenantId, Long id, EmpStatus... statuses) {

        String sql = buildSqlExistsByIdAndStatus(statuses.length);

        Object[] params = buildParamsExistsByIdAndStatus(tenantId, id, statuses);


        try {
            return jdbc.queryForObject(sql, Integer.class, params) != null;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    private static Object[] buildParamsExistsByIdAndStatus(Long tenantId, Long id, EmpStatus[] statuses) {
        Object[] params = new Object[2 + statuses.length];
        params[0] = tenantId;
        params[1] = id;
        int j = 2;
        for(EmpStatus status: statuses) {
           params[j++] = status.code();
        }
        return params;
    }

    private static String buildSqlExistsByIdAndStatus(int statusCount) {
        String status_condition = "";
        for (int i = 0; i < statusCount; i++) {
            if (i == 0) {
                status_condition = status_condition + " and (status_code = ?";
            } else {
                status_condition = status_condition + "or status_code = ?";
            }
        }
        status_condition += ")";

        String sql = " select 1 from emp  where tenant_id = ? and id = ?"
                + status_condition
                + " limit 1 ";
        return sql;
    }
}
