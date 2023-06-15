package demo.unjuanable.domain.common.validator;

import demo.unjuanable.adapter.driven.persistence.tenantmng.TenantRepositoryJdbc;
import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonTenantValidator {
    private final TenantRepositoryJdbc tenantRepository;

    @Autowired
    public CommonTenantValidator(TenantRepositoryJdbc tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public void shouldEffective(Long tenant) {
        // 租户必须有效
        if (!tenantRepository.existsByIdAndStatus(tenant, TenantStatus.EFFECTIVE)) {
            throw new BusinessException("id为'" + tenant + "'的租户不是有效租户！");
        }
    }

}