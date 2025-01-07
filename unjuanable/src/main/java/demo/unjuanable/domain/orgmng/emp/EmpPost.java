package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;
import demo.unjuanable.common.framework.domain.ChangingStatus;

import java.time.LocalDateTime;

import static demo.unjuanable.common.framework.domain.ChangingStatus.UNCHANGED;

public class EmpPost extends AuditableEntity {
    final private Emp emp;
    private String postCode;

    //用于新建
    EmpPost(Emp emp, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.emp = emp;
    }

    // 用于从数据库加载
    EmpPost(Emp emp
            , String postCode
            , LocalDateTime createdAt
            , Long createdBy
            , LocalDateTime lastUpdatedAt
            , Long lastUpdatedBy ) {
        super(createdAt, createdBy);
        this.changingStatus = UNCHANGED;
        this.emp = emp;
        this.postCode = postCode;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Long getEmpId() {
        return emp.getId();
    }
}
