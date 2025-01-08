package demo.unjuanable.application.orgmng.orgservice.dto;

public class CreateOrgRequest {
    private Long tenant;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;

    public Long getTenantId() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public Long getSuperiorId() {
        return superior;
    }

    public void setSuperior(Long superior) {
        this.superior = superior;
    }

    public String getOrgTypeCode() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Long getLeaderId() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrgDto{ tenant=" + tenant +
                ", superior=" + superior +
                ", orgType='" + orgType + '\'' +
                ", leader=" + leader +
                ", name='" + name + '\'' +
                '}';
    }
}
