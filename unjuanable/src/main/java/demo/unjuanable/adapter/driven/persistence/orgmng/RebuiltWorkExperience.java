package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.ChangingStatus;
import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;

import java.time.LocalDateTime;

public class RebuiltWorkExperience extends WorkExperience {
    public RebuiltWorkExperience(Long tenantId , Long id
            , Period period, LocalDateTime createdAt, Long createdBy) {

        super(tenantId, id, period, createdAt, createdBy) ;
        this.changingStatus = ChangingStatus.UNCHANGED;
    }

    public RebuiltWorkExperience resetCompany(String level) {
        this.company = level;
        return this;
    }
}
