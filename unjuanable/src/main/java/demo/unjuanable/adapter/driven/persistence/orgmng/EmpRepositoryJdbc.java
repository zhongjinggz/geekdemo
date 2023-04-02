package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpRepositoryJdbc implements EmpRepository {

    JdbcTemplate jdbc;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int countByIdAndStatus(long tenantId, long id, EmpStatus... statuses) {
        final String sql = "select count(*) from emp " +
                "where tenant = ? " +
                "and id = ? ";

        if (statuses.length > 0) {
            StringBuilder orSql = new StringBuilder(sql).append("and (status = '' ");
            for (EmpStatus status : statuses) {
                orSql.append("or status = '").append(status.code()).append("' ");
            }
            orSql.append(")");
        }

        return jdbc.queryForObject(sql, Integer.class, tenantId, id);
    }

    @Override
    public boolean existsByIdAndStatus(Long tenant, Long id, EmpStatus... statuses) {
        return countByIdAndStatus(tenant, id , statuses) > 0;
    }
}
