package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class OrgRepositoryJdbc
        extends Persister<Org> implements OrgRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insertOrg;

    public OrgRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insertOrg = new SimpleJdbcInsert(jdbc)
                .withTableName("org")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    protected void insert(Org org) {
        Map<String, Object> args = new HashMap<>(8);

        args.put("created_at", org.getCreatedAt());
        args.put("created_by", org.getCreatedBy());
        args.put("leader_id", org.getLeaderId());
        args.put("name", org.getName());
        args.put("org_type_code", org.getOrgTypeCode());
        args.put("status_code", org.getStatus().code());
        args.put("superior_id", org.getSuperiorId());
        args.put("tenant_id", org.getTenantId());

        Number createdId = insertOrg.executeAndReturnKey(args);

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
        return selectOneOrg(sql, tenantId, id);

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
        return selectOneOrg(sql, tenantId, id, status.code());
    }

    private Optional<Org> selectOneOrg(String sql, Object... args) {
        List<Org> orgList = selectOrgs(sql, args);
        return orgList.isEmpty() ? Optional.empty() : Optional.of(orgList.getFirst());
    }

    private List<Org> selectOrgs(String sql, Object... args) {
        List<Map<String, Object>> orgMaps = jdbc.queryForList(sql, args);

        return orgMaps.stream().map(org -> Org.loader()
                        .tenantId((Long) org.get("tenant_id"))
                        .id((Long) org.get("id"))
                        .superiorId((Long) org.get("superior_id"))
                        .orgTypeCode((String) org.get("org_type_code"))
                        .leaderId((Long) org.get("leader_id"))
                        .name((String) org.get("name"))
                        .statusCode((String) org.get("status_code"))
                        .createdAt((LocalDateTime) org.get("created_at"))
                        .createdBy((Long) org.get("created_by"))
                        .lastUpdatedAt((LocalDateTime) org.get("last_updated_at"))
                        .lastUpdatedBy((Long) org.get("last_updated_by"))
                        .load())
                .toList();
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

    private boolean selectExists(String sql, Object... args) {
        return !jdbc
                .queryForList(sql, args)
                .isEmpty();
    }
}
