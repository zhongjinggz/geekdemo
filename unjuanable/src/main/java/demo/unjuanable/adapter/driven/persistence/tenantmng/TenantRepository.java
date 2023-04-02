package demo.unjuanable.adapter.driven.persistence.tenantmng;

import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public TenantRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int countById(long id) {
        final String countSql = "select count(*) from tenant where id = ?";
        return jdbc.queryForObject(countSql, Integer.class, id);
    }

    public int countByIdAndStatus(long id, TenantStatus status) {
        final String countSql = "select count(*) from tenant where id = ? and status = ?";
        return jdbc.queryForObject(countSql, Integer.class, id, status.code());
    }

    public boolean existsByIdAndStatus(long tenant, TenantStatus status) {
        return countByIdAndStatus(tenant , status) > 0;
    }
}
