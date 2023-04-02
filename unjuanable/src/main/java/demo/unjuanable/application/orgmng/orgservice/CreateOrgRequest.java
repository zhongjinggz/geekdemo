package demo.unjuanable.application.orgmng.orgservice;

public class CreateOrgRequest {
    private Long tenant;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public Long getSuperior() {
        return superior;
    }

    public void setSuperior(Long superior) {
        this.superior = superior;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Long getLeader() {
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
