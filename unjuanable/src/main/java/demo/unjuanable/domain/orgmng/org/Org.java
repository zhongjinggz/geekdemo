package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class Org extends AuditableEntity {
    private Long id;
    private Long tenant;
    private Long superior;
    private String orgType;
    private Long leader;
    private String name;
    private OrgStatus status;

    public Org(Long tenant, String orgType, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.tenant = tenant;
        this.orgType = orgType;
        status = OrgStatus.EFFECTIVE;
    }

    public Long getId() {
        return id;
    }

    public Long getTenant() {
        return tenant;
    }

    public Long getSuperior() {
        return superior;
    }

    public String getOrgType() {
        return orgType;
    }

    public Long getLeader() {
        return leader;
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

    public void setSuperior(Long superior) {
        this.superior = superior;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
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
}
