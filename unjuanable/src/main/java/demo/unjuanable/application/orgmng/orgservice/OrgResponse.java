package demo.unjuanable.application.orgmng.orgservice;

// tenant should not blank
// tenant should be valid
// superior ...
// org type should be valid
// leader should be valid
// name should not blank
// created user should not blank

import java.time.LocalDateTime;

public class OrgResponse {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }


    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }


    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return "OrgDto{" + "id=" + id +
                ", tenant=" + tenantId +
                ", superior=" + superiorId +
                ", orgType='" + orgTypeCode + '\'' +
                ", leader=" + leaderId +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", lastUpdatedBy=" + lastUpdatedBy +
                '}';
    }
}
