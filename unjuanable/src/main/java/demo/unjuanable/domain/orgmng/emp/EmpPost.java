package demo.unjuanable.domain.orgmng.emp;


import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class EmpPost extends AuditableEntity {
    String postCode;

    public EmpPost(LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
