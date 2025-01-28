package demo.unjuanable.domain.tenantmng;

import demo.unjuanable.common.framework.domain.AggregateRoot;
import demo.unjuanable.common.framework.domain.AuditableEntity;
import demo.unjuanable.common.framework.domain.ChangingStatus;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.*;

public class Tenant extends AggregateRoot {
    private Long id;
    private String name;
    private TenantStatus status;

    // 用于新建
    public Tenant(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.status = TenantStatus.EFFECTIVE;
        this.version = 0L;
        this.changingStatus = NEW;
    }

    // 用于从数据库中加载
    // private 避免了程序员误用
    // mybatis 会使用反射机制调用
    private Tenant(@Param("id") Long id
            , @Param("name") String name
            , @Param("statusCode") String statusCode
            , @Param("createdAt") LocalDateTime createdAt
            , @Param("createdBy") Long createdBy
            , @Param("lastUpdatedAt") LocalDateTime lastUpdatedAt
            , @Param("lastUpdatedBy") Long lastUpdatedBy
    ) {
        super(createdAt, createdBy);
        this.id = id;
        this.name = name;
        this.status = TenantStatus.ofCode(statusCode);
        this.changingStatus = UNCHANGED;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public TenantStatus getStatus() {
        return status;
    }
}
