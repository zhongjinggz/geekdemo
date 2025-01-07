package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class EmpPost extends AuditableEntity {
    Emp emp;
    String postCode;

    public EmpPost(Emp emp, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.emp = emp;
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
