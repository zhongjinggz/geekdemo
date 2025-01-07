package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;

import java.time.LocalDateTime;

public class RebuiltWorkExperience extends WorkExperience {
    public RebuiltWorkExperience(Emp emp, Long tenantId , Long id
            , Period period, LocalDateTime createdAt, Long createdBy) {

        super(emp, tenantId, id, period, createdAt, createdBy) ;
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    public RebuiltWorkExperience resetCompany(String level) {
        this.company = level;
        return this;
    }
}
