package demo.unjuanable.domain.orgmng.orgtype;


import demo.unjuanable.common.framework.domain.AggregateRoot;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.UNCHANGED;

public class OrgType extends AggregateRoot {

    private String code;
    private long tenantId;
    private String name;
    private OrgTypeStatus status;

    // 用于新建
    public OrgType(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
    }

    // 用于从数据库加载
    public OrgType(String code
            , long tenantId
            , String name
            , String statusCode
            , LocalDateTime createdAt
            , Long createdBy
            , LocalDateTime lastUpdatedAt
            , Long lastUpdatedBy
            , Long version
    ) {
        super(createdAt, createdBy);
        this.changingStatus = UNCHANGED;
        this.code = code;
        this.tenantId = tenantId;
        this.name = name;
        this.status = OrgTypeStatus.ofCode(statusCode);
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
        this.version = version;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrgTypeStatus getStatus() {
        return status;
    }

    public void setStatus(OrgTypeStatus status) {
        this.status = status;
    }

}
