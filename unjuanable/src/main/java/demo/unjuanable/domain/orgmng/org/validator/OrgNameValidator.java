package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class OrgNameValidator {
    private final OrgRepository orgRepository;

    @Autowired
    OrgNameValidator(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    // 组织必须有名称
    public void shouldNotEmpty(String name) {
        //        return str == null || str.trim().isEmpty();
        if (isBlank(name)) {
            throw new BusinessException("组织没有名称！");
        }
    }

    // 同一个组织下的下级组织不能重名
    public void shouldNotDuplicatedInSameSuperior(Long tenantId, Long superiorId, String name) {
        if (orgRepository.existsBySuperiorIdAndName(tenantId, superiorId, name)) {
            throw new BusinessException("同一上级下已经有名为'" + name + "'的组织存在！");
        }
    }
}
