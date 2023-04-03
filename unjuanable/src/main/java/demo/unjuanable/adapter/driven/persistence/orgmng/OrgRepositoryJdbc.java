package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;
import static demo.unjuanable.common.util.SqlUtil.toLocalDateTime;

@Repository
public class OrgRepositoryJdbc implements OrgRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insertOrg;
    private final OrgReBuilderFactory orgReBuilderFactory;
    private final RowMapper<Org> orgRowMapper = new OrgRowMapper();

    @Autowired
    public OrgRepositoryJdbc(JdbcTemplate jdbc, OrgReBuilderFactory orgReBuilderFactory) {
        this.jdbc = jdbc;
        this.insertOrg = new SimpleJdbcInsert(jdbc)
                .withTableName("org")
                .usingGeneratedKeyColumns("id");

        this.orgReBuilderFactory = orgReBuilderFactory;

    }

    @Override
    public Optional<Org> findByIdAndStatus(Long tenantId, Long id, OrgStatus status) {
        final String sql = " select id"
                + ", tenant_id"
                + ", superior_id"
                + ", org_type_code"
                + ", leader_id"
                + ", name"
                + ", status_code"
                + ", created_at"
                + ", created_by"
                + ", last_updated_at"
                + ", last_updated_by "
                + " from org "
                + " where tenant_id = ?  and id = ? and status_code = ? ";

        return Optional.ofNullable(
                jdbc.queryForObject(sql
                        , orgRowMapper
                        , tenantId
                        , id
                        , status.code()));
    }

    @Override
    public Org save(Org org) {
        Map<String, Object> parms = new HashMap<>(3);

        parms.put("created_at", org.getCreatedAt());
        parms.put("created_by", org.getCreatedBy());
        parms.put("leader_id", org.getLeaderId());
        parms.put("name", org.getName());
        parms.put("org_type_code", org.getOrgTypeCode());
        parms.put("status_code", org.getStatus().code());
        parms.put("superior_id", org.getSuperiorId());
        parms.put("tenant_id", org.getTenantId());

        Number createdId = insertOrg.executeAndReturnKey(parms);

        forceSet(org, "id", createdId.longValue());

        return org;
    }

    @Override
    public Optional<Org> findById(Long tenantId, Long id) {
        final String sql = " select id"
                + ", tenant_id"
                + ", superior_id"
                + ", org_type_code"
                + ", leader_id"
                + ", name"
                + ", status_code"
                + ", created_at"
                + ", created_by"
                + ", last_updated_at"
                + ", last_updated_by "
                + " from org "
                + " where tenant_id = ?  and id = ? ";

        return Optional.ofNullable(
                jdbc.queryForObject(sql
                        , orgRowMapper
                        , tenantId
                        , id)
        );
    }

    @Override
    public boolean existsBySuperiorAndName(Long tenant, Long superior, String name) {
        return false;
    }

    @Override
    public int update(Org org) {
        return 0;
    }


    class OrgRowMapper implements RowMapper<Org> {
        @Override
        public Org mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrgReBuilder orgReBuilder = orgReBuilderFactory.build();
            return orgReBuilder.id(rs.getLong("id"))
                    .tenantId(rs.getLong("tenant_id"))
                    .superiorId(rs.getLong("superior_id"))
                    .orgTypeCode(rs.getString("org_type_code"))
                    .leaderId(rs.getLong("leader_id"))
                    .name(rs.getString("name"))
                    .statusCode(rs.getString("status_code"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .createdBy(rs.getLong("created_by"))
                    .lastCreatedAt(toLocalDateTime(rs, "last_updated_at"))
                    .lastUpdatedBy(rs.getLong("last_updated_by"))
                    .build();
        }
    }
}
