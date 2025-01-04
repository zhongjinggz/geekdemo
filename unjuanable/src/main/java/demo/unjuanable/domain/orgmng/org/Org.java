package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class Org extends AuditableEntity {
    private final Long tenantId;
    private Long id;
    private Long superiorId;
    private String orgTypeCode;
    private Long leaderId;
    private String name;
    private OrgStatus status;

    public Org(Long tenantId, String orgTypeCode, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.tenantId = tenantId;
        this.orgTypeCode = orgTypeCode;
        status = OrgStatus.EFFECTIVE;
    }

    Org(Long tenantId
            , Long id
            , Long superiorId
            , String orgTypeCode
            , Long leaderId
            , String name
            , String statusCode
            , LocalDateTime createdAt
            , Long createdBy
            , LocalDateTime lastUpdatedAt
            , Long lastUpdatedBy
    ) {
        super(createdAt, createdBy);
        this.tenantId = tenantId;
        this.id = id;
        this.superiorId = superiorId;
        this.orgTypeCode = orgTypeCode;
        this.leaderId = leaderId;
        this.name = name;
        this.status = OrgStatus.ofCode(statusCode);
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public String getName() {
        return name;
    }

    public OrgStatus getStatus() {
        return status;
    }

    public boolean isEffective() {
        return status.equals(OrgStatus.EFFECTIVE);
    }

    public boolean isCanceled() {
        return status.equals(OrgStatus.CANCELLED);
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(OrgStatus status) {
        this.status = status;
    }

    public void cancel() {
        this.status = OrgStatus.CANCELLED;
    }

    void setId(Long id) {
        this.id = id;
    }
}
