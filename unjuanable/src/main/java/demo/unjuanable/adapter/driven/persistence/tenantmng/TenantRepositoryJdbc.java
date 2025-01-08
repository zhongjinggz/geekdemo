package demo.unjuanable.adapter.driven.persistence.tenantmng;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.tenantmng.Tenant;
import demo.unjuanable.domain.tenantmng.TenantRepository;
import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class TenantRepositoryJdbc extends Mapper<Tenant> implements TenantRepository {
    public TenantRepositoryJdbc(JdbcTemplate jdbc) {
        super(jdbc, "tenant", "id");
    }

    public void insert(Tenant tenant) {
        Map<String, Object> args = new HashMap<>(3);

        args.put("name", tenant.getName());
        args.put("status_code", tenant.getStatus().code());
        args.put("created_at", tenant.getCreatedAt());
        args.put("created_by", tenant.getCreatedBy());

        Number createdId = jdbcInsert.executeAndReturnKey(args);

        forceSet(tenant, "id", createdId.longValue());
    }

    @Override
    public boolean existsByIdAndStatus(long tenantId, TenantStatus status) {

        final String sql = " select 1 from tenant "
                + " where id = ?  and status_code = ?"
                + " limit 1 ";

        return selectExists(sql, tenantId, status.code());
    }
}