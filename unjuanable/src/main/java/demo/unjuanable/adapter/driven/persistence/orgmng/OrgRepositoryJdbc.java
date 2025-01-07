package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class OrgRepositoryJdbc
        extends Mapper<Org> implements OrgRepository {


    public OrgRepositoryJdbc(JdbcTemplate jdbc) {
        super(jdbc, "org", "id");
    }

    @Override
    protected void insert(Org org) {
        Map<String, Object> params = new HashMap<>(8);

        params.put("created_at", org.getCreatedAt());
        params.put("created_by", org.getCreatedBy());
        params.put("leader_id", org.getLeaderId());
        params.put("name", org.getName());
        params.put("org_type_code", org.getOrgTypeCode());
        params.put("status_code", org.getStatus().code());
        params.put("superior_id", org.getSuperiorId());
        params.put("tenant_id", org.getTenantId());

        Number createdId = jdbcInsert.executeAndReturnKey(params);

        forceSet(org, "id", createdId.longValue());
    }

    @Override
    protected void update(Org org) {
        String sql = "update org "
                + " set superior_id = ? "
                + ", org_type_code =? "
                + ", leader_id = ?"
                + ", name = ?"
                + ", status_code =?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ? "
                + " where tenant_id = ? and id = ? ";

        this.jdbc.update(sql
                , org.getSuperiorId()
                , org.getOrgTypeCode()
                , org.getLeaderId()
                , org.getName()
                , org.getStatus().code()
                , org.getLastUpdatedAt()
                , org.getLastUpdatedBy()
                , org.getTenantId()
                , org.getId()
        );
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
        return selectOne(sql
                , mapToOrg()
                , tenantId, id);

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
        return selectOne(sql
                , mapToOrg()
                , tenantId, id, status.code());
    }

    private Function<Map<String, Object>, Org> mapToOrg() {
        return orgMap -> Org.loader()
                .tenantId((Long) orgMap.get("tenant_id"))
                .id((Long) orgMap.get("id"))
                .superiorId((Long) orgMap.get("superior_id"))
                .orgTypeCode((String) orgMap.get("org_type_code"))
                .leaderId((Long) orgMap.get("leader_id"))
                .name((String) orgMap.get("name"))
                .statusCode((String) orgMap.get("status_code"))
                .createdAt((LocalDateTime) orgMap.get("created_at"))
                .createdBy((Long) orgMap.get("created_by"))
                .lastUpdatedAt((LocalDateTime) orgMap.get("last_updated_at"))
                .lastUpdatedBy((Long) orgMap.get("last_updated_by"))
                .load();
    }

    @Override
    public boolean existsBySuperiorIdAndName(Long tenantId, Long superiorId, String name) {
        final String sql = " select 1 from org "
                + " where tenant_id = ?  and superior_id = ? and name = ?"
                + " limit 1 ";

        return selectExists(sql, tenantId, superiorId, name);
    }

    @Override
    public boolean existsByIdAndStatus(Long tenantId, Long id, OrgStatus status) {
        String sql = " select 1 from org "
                + " where tenant_id = ?  and id = ? and status_code = ?"
                + " limit 1 ";
        return selectExists(sql, tenantId, id, status.code());
    }

}
