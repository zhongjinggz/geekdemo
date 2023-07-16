package demo.unjuanable.adapter.driven.persistence.tenantmng;

import demo.unjuanable.domain.tenantmng.Tenant;
import demo.unjuanable.domain.tenantmng.TenantRepository;
import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class TenantRepositoryJdbc implements TenantRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insertTenant;

    @Autowired
    public TenantRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insertTenant = new SimpleJdbcInsert(jdbc)
                .withTableName("tenant")
                .usingGeneratedKeyColumns("id");
    }

    public Tenant save(Tenant tenant) {
        Map<String, Object> parms = new HashMap<>(3);

        parms.put("name", tenant.getName());
        parms.put("status_code", tenant.getStatus().code());
        parms.put("created_at", tenant.getCreatedAt());
        parms.put("created_by", tenant.getCreatedBy());

        Number createdId = insertTenant.executeAndReturnKey(parms);

        forceSet(tenant, "id", createdId.longValue());

        return tenant;
    }


    @Override
    public boolean existsByIdAndStatus(long tenantId, TenantStatus status) {

        final String sql = " select 1 from tenant "
                + " where id = ?  and status_code = ?"
                + " limit 1 ";

        try {
            return jdbc.queryForObject(sql, Integer.class, tenantId, status.code()) != null;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }

    }


}
