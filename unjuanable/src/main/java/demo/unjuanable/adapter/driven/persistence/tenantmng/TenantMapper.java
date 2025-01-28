package demo.unjuanable.adapter.driven.persistence.tenantmng;

import demo.unjuanable.domain.tenantmng.Tenant;
import org.apache.ibatis.annotations.Param;

public interface TenantMapper {
    void insert(Tenant tenant);

    int update(Tenant tenant);

    Tenant selectById(@Param("id") long id);

    Integer selectExistByIdAndStatus(@Param("id") long id
            , @Param("statusCode") String statusCode);


}
