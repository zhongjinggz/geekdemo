package demo.unjuanable.domain.common.validator;

import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import demo.unjuanable.domain.orgmng.org.OrgStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonOrgValidator {
    private final OrgRepository orgRepository;

    @Autowired
    public CommonOrgValidator(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public void shouldValid(Long tenantId, Long orgId) {
        if (!orgRepository.existsByIdAndStatus(tenantId, orgId, OrgStatus.EFFECTIVE)){
            throw new BusinessException("id为'" + orgId + "'的组织不是有效组织！");
        }
    }



}