package demo.unjuanable.adapter.driven.persistence.orgmng.orgtype;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.common.util.SqlUtil;
import demo.unjuanable.domain.orgmng.orgtype.OrgType;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeRepository;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class OrgTypeRepositoryJdbc extends Mapper<OrgType> implements OrgTypeRepository {
    public OrgTypeRepositoryJdbc(JdbcTemplate jdbc) {
        super(jdbc, "org_type");
    }

    public Optional<OrgType> findByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String sql = "select  code, tenant_id, name, status_code" +
                ", created_at, created_by, last_updated_at, last_updated_by " +
                "from org_type " +
                "where tenant_id = ? and code = ? and status_code = ?";

        return selectOne(sql , mapToOrgType() , tenantId, code, status.code());
    }

    private Function<Map<String, Object>, OrgType> mapToOrgType() {
        return orgTypeMap ->
            new OrgType(
                    (String) orgTypeMap.get("code")
                    , (Long) orgTypeMap.get("tenant_id")
                    , (String) orgTypeMap.get("name")
                    , (String) orgTypeMap.get("status_code")
                    , (LocalDateTime) orgTypeMap.get("created_at")
                    , (Long) orgTypeMap.get("created_by")
                    , (LocalDateTime) orgTypeMap.get("last_updated_at")
                    , (Long) orgTypeMap.get("last_updated_by")
                    , (Long) orgTypeMap.get("version")) ;
    }

    @Override
    public boolean existsByCodeAndStatus(long tenantId, String code, OrgTypeStatus status) {
        final String sql = "select 1"
                + " from org_type "
                + " where tenant_id = ? and code = ? and status_code = ?"
                + " limit 1";
        return selectExists(sql, tenantId, code, status.code());
    }
}
