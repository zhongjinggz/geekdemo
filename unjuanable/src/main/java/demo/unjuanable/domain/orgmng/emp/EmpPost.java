package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;
import demo.unjuanable.common.framework.domain.ChangingStatus;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.UNCHANGED;

public class EmpPost extends AuditableEntity {
    private Emp emp;
    private String postCode;

    //用于新建
    EmpPost(Emp emp, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.emp = emp;
    }

    // 用于从数据库加载
    public EmpPost(String postCode
            , LocalDateTime createdAt
            , Long createdBy
            , LocalDateTime lastUpdatedAt
            , Long lastUpdatedBy) {
        super(createdAt, createdBy);
        this.changingStatus = UNCHANGED;
        this.postCode = postCode;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getPostCode() {
        return postCode;
    }

    public EmpPost setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public Long getEmpId() {
        return emp.getId();
    }

    EmpPost setEmp(Emp emp) {
        this.emp = emp;
        return this;
    }
}
