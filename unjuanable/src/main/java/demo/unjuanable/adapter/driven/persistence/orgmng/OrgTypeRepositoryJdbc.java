package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.util.SqlUtil;
import demo.unjuanable.domain.orgmng.orgtype.OrgType;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeRepository;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrgTypeRepositoryJdbc implements OrgTypeRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public OrgTypeRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int countByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String countSql = "select count(*) " +
                "from org_type " +
                "where tenant_id = ? and code = ? and status_code = ?";
        return jdbc.queryForObject(countSql, Integer.class, tenantId, code, status.code());
    }

    public Optional<OrgType> findByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String sql = "select  code, tenant_id, name, status_code" +
                ", created_at, created_by, last_updated_at, last_updated_by " +
                "from org_type " +
                "where tenant_id = ? and code = ? and status_code = ?";

        return Optional.ofNullable(
                jdbc.queryForObject(sql,
                        (rs, rowNum) -> {
                            OrgType orgType = new OrgType(SqlUtil.toLocalDateTime(rs, "created_at")
                                    , rs.getLong("created_by")
                            );
                            orgType.setCode(rs.getString("code"));
                            orgType.setTenant(rs.getLong("tenant_id"));
                            orgType.setName(rs.getString("name"));
                            orgType.setStatus(OrgTypeStatus.ofCode(rs.getString("status_code")));
                            orgType.setLastUpdatedAt(SqlUtil.toLocalDateTime(rs, "last_updated_at"));
                            orgType.setLastUpdatedBy(rs.getLong("last_updated_by"));
                            return orgType;
                        }
                        , tenantId
                        , code
                        , status.code()));
    }

    @Override
    public boolean existsByCodeAndStatus(long tenant, String code, OrgTypeStatus status) {
        return countByCodeAndStatus(tenant, code
                , status) > 0;
    }
}
