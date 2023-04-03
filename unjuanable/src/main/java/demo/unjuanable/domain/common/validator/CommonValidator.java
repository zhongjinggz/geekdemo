package demo.unjuanable.domain.common.validator;

import demo.unjuanable.adapter.driven.persistence.tenantmng.TenantRepository;
import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonValidator {
    private final TenantRepository tenantRepository;

    @Autowired
    public CommonValidator(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public void tenantShouldValid(Long tenant) {
        // 租户必须有效
        if (!tenantRepository.existsByIdAndStatus(tenant, TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + tenant + "'的租户不是有效租户！");
        }
    }

}