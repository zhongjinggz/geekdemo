package demo.unjuanable.domain.orgmng.orgtype;


import java.time.LocalDateTime;

public class OrgType {

  private String code;
  private long tenant;
  private String name;
  private OrgType status;
  private LocalDateTime createdAt;
  private long createdBy;
  private LocalDateTime lastUpdatedAt;
  private long lastUpdatedBy;


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


  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }


  public LocalDateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }


  public long getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(long lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }


  public OrgType getStatus() {
    return status;
  }

  public void setStatus(OrgType status) {
    this.status = status;
  }

}
