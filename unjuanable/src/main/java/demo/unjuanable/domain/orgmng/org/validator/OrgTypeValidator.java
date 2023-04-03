package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeRepository;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static demo.unjuanable.common.util.StringUtil.isBlank;

@Component
public class OrgTypeValidator {
    private final OrgTypeRepository orgTypeRepository;

    @Autowired
    OrgTypeValidator(OrgTypeRepository orgTypeRepository) {
        this.orgTypeRepository = orgTypeRepository;
    }

    // 组织类别不能为空
    public void shouldNotEmpty(String orgType) {
        if (isBlank(orgType)) {
            throw new BusinessException("组织类别不能为空！");
        }
    }

    // 组织类别必须有效
    public void shouldEffective(Long tenant, String orgType) {
        if (!orgTypeRepository.existsByCodeAndStatus(tenant, orgType, OrgTypeStatus.EFFECTIVE)) {
            throw new BusinessException("'" + orgType + "'不是有效的组织类别代码！");
        }
    }

    // 企业是在创建租户的时候创建好的，因此不能单独创建企业
    public void shouldNotEntp(String orgType) {
        if ("ENTP".equals(orgType)) {
            throw new BusinessException("企业是在创建租户的时候创建好的，因此不能单独创建企业!");
        }
    }

}
