package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import demo.unjuanable.domain.orgmng.org.OrgStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class OrgRepositoryJdbc implements OrgRepository {
    JdbcTemplate jdbc;
    SimpleJdbcInsert insertOrg;

    @Autowired
    public OrgRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insertOrg = new SimpleJdbcInsert(jdbc)
                .withTableName("org")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Optional<Org> findByIdAndStatus(Long tenantId, Long id, OrgStatus status) {
        return Optional.empty();
    }

    @Override
    public Org save(Org org) {
        Map<String, Object> parms = new HashMap<>(3);

        parms.put("created_at", org.getCreatedAt());
        parms.put("created_by", org.getCreatedBy());
        parms.put("leader", org.getLeader());
        parms.put("name", org.getName());
        parms.put("org_type", org.getOrgType());
        parms.put("status", org.getStatus().code());
        parms.put("superior", org.getSuperior());
        parms.put("tenant", org.getTenant());

        Number createdId = insertOrg.executeAndReturnKey(parms);

        forceSet(org, "id", createdId.longValue());

        return org;
    }

    @Override
    public Optional<Org> findById(Long tenantId, Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsBySuperiorAndName(Long tenant, Long superior, String name) {
        return false;
    }

    @Override
    public int update(Org org) {
        return 0;
    }
}
