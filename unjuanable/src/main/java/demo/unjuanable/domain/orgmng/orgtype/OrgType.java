package demo.unjuanable.domain.orgmng.orgtype;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class OrgType extends AuditableEntity {

  private String code;
  private long tenant;
  private String name;
  private OrgTypeStatus status;

  public OrgType(LocalDateTime createdAt, Long createdBy) {
    super(createdAt, createdBy);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public long getTenant() {
    return tenant;
  }

  public void setTenant(long tenant) {
    this.tenant = tenant;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }


  public void setLastUpdatedBy(long lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }


  public OrgTypeStatus getStatus() {
    return status;
  }

  public void setStatus(OrgTypeStatus status) {
    this.status = status;
  }

}
