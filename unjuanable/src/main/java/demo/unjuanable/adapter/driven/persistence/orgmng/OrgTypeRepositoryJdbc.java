package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.orgtype.OrgType;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeRepository;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrgTypeRepositoryJdbc implements OrgTypeRepository {
    JdbcTemplate jdbc;

    @Autowired
    public OrgTypeRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int countByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String countSql = "select count(*) " +
                "from org_type " +
                "where tenant = ? and code = ? and status = ?";
        return jdbc.queryForObject(countSql, Integer.class, tenantId, code, status.code());
    }

    public Optional<OrgType> findByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String sql = "select  code, tenant, name, status" +
                ", created_at, created_by, last_updated_at, last_updated_by " +
                "from org_type " +
                "where tenant = ? and code = ? and status = ?";

        return Optional.ofNullable(jdbc.queryForObject(sql, OrgType.class, tenantId
                , code, status.code()));
    }

    @Override
    public boolean existsByCodeAndStatus(long tenant, String code, OrgTypeStatus status) {
        return countByCodeAndStatus(tenant , code
                , status) > 0;
    }
}
