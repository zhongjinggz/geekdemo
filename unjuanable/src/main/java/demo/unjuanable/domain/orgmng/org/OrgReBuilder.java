package demo.unjuanable.domain.orgmng.org;


import java.time.LocalDateTime;

// 用于从数据库重建Org，假设数据库中的数据时干净的，因此不需要对数据进行校验。
public class OrgReBuilder {
    private Long id;
    private Long tenantId;
    private Long superiorId;
    private String orgTypeCode;
    private Long leaderId;
    private String name;
    private String statusCode;

    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastUpdatedAt;
    private Long lastUpdatedBy;

    public OrgReBuilder tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public OrgReBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrgReBuilder superiorId(Long superiorId) {
        this.superiorId = superiorId;
        return this;
    }

    public OrgReBuilder orgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
        return this;
    }

    public OrgReBuilder leaderId(Long leaderId) {
        this.leaderId = leaderId;
        return this;
    }

    public OrgReBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OrgReBuilder statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public OrgReBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrgReBuilder createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OrgReBuilder lastCreatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public OrgReBuilder lastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public Org build() {
        Org org = new Org(tenantId, orgTypeCode, createdAt, createdBy);
        org.setId(id);
        org.setSuperiorId(this.superiorId);
        org.setLeaderId(this.leaderId);
        org.setName(this.name);
        org.setStatus(OrgStatus.ofCode(statusCode));
        org.setLastUpdatedAt(lastUpdatedAt);
        org.setLastUpdatedBy(lastUpdatedBy);
        return org;
    }

}
