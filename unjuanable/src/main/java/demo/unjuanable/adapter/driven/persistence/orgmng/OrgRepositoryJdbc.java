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
public class OrgRepositoryJdbc implements OrgRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insertOrg;
    private final OrgReBuilderFactory orgReBuilderFactory;

    public OrgRepositoryJdbc(JdbcTemplate jdbc, OrgReBuilderFactory orgReBuilderFactory) {
        this.jdbc = jdbc;
        this.insertOrg = new SimpleJdbcInsert(jdbc)
                .withTableName("org")
                .usingGeneratedKeyColumns("id");

        this.orgReBuilderFactory = orgReBuilderFactory;
    }


    @Override
    public Org save(Org org) {
        Map<String, Object> parms = new HashMap<>(8);

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
    public int update(Org org) {
        String sql = "update org "
                + " set superior_id = ? "
                + ", org_type_code =? "
                + ", leader_id = ?"
                + ", name = ?"
                + ", status_code =?"
                + ", last_updated_at = ?"
                + ", last_updated_by = ? "
                + " where tenant_id = ? and id = ? ";

        return this.jdbc.update(sql
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

        return orgMaps.stream().map(org ->
                orgReBuilderFactory.newBuilder()
                        .id((Long) org.get("id"))
                        .tenantId((Long) org.get("tenant_id"))
                        .superiorId((Long) org.get("superior_id"))
                        .orgTypeCode((String) org.get("org_type_code"))
                        .leaderId((Long) org.get("leader_id"))
                        .name((String) org.get("name"))
                        .statusCode((String) org.get("status_code"))
                        .createdAt((LocalDateTime) org.get("created_at"))
                        .createdBy((Long) org.get("created_by"))
                        .lastCreatedAt((LocalDateTime) org.get("last_updated_at"))
                        .lastUpdatedBy((Long) org.get("last_updated_by"))
                        .build()).toList();
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
