package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;

import java.time.LocalDateTime;

public class RebuiltWorkExperience extends WorkExperience {
    RebuiltWorkExperience(Long tenantId, Period period, LocalDateTime createdAt, Long createdBy) {
        super(tenantId, period, createdAt, createdBy) ;
    }

    RebuiltWorkExperience resetCompany(String level) {
        this.company = level;
        return this;
    }
}
