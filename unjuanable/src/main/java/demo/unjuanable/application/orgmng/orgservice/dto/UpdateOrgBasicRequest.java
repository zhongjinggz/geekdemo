package demo.unjuanable.application.orgmng.orgservice.dto;

// tenant should not blank
// tenant should be valid
// superior ...
// org type should be valid
// leader should be valid
// name should not blank
// created user should not blank

public class UpdateOrgBasicRequest {
    private Long tenantId;
    private Long leaderId;
    private String name;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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

    @Override
    public String toString() {
        return "OrgDto{" + " tenant=" + tenantId +
                ", leader=" + leaderId +
                ", name='" + name + '\'' +
                '}';
    }
}
