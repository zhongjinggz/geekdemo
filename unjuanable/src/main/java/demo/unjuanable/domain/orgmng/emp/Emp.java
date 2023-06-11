package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.common.framework.domain.AggregateRoot;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Emp extends AggregateRoot {
    protected Long id;
    protected Long tenantId;
    protected Long orgId;
    protected String num;
    protected String idNum;

    protected String name;
    protected Gender gender;
    protected LocalDate dob;
    protected EmpStatus status;


    // 用于新建员工
    public Emp(Long tenantId, Long createdBy) {
        super(LocalDateTime.now(), createdBy);
        this.tenantId = tenantId;
    }

    //用于从数据库重建员工
    public Emp(Long tenantId, Long id, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.id = id;
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public Emp setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getNum() {
        return num;
    }

    public Emp setNum(String num) {
        this.num = num;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public Emp setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public Emp setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Emp setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public EmpStatus getStatus() {
        return status;
    }

}
