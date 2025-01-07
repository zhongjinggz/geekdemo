package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.adapter.driven.persistence.orgmng.orgtype.OrgTypeRepositoryJdbc;
import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.common.framework.exception.DirtyDataException;
import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import demo.unjuanable.domain.orgmng.org.OrgStatus;
import demo.unjuanable.domain.orgmng.orgtype.OrgType;
import demo.unjuanable.domain.orgmng.orgtype.OrgTypeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuperiorValidator {
    private final OrgTypeRepositoryJdbc orgTypeRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public SuperiorValidator(OrgTypeRepositoryJdbc orgTypeRepository, OrgRepository orgRepository) {
        this.orgTypeRepository = orgTypeRepository;
        this.orgRepository = orgRepository;
    }

    // 上级组织应该是有效组织
    public Org shouldEffective(Long tenant, Long superior) {
        return orgRepository.findByIdAndStatus(tenant
                        , superior, OrgStatus.EFFECTIVE)
                .orElseThrow(() ->
                        new BusinessException("'" + superior + "' 不是有效的组织 id !"));
    }

    public OrgType orgTypeShouldEffective(Long tenant, Long superior, Org superiorOrg) {
        return orgTypeRepository.findByCodeAndStatus(tenant
                        , superiorOrg.getOrgTypeCode()
                        , OrgTypeStatus.EFFECTIVE)
                .orElseThrow(() ->
                        new DirtyDataException("id 为 '" + superior
                                + "' 的组织的组织类型代码 '" + superiorOrg.getOrgTypeCode() + "' 无效!"));
    }

    // 开发中心和直属部门的上级只能是企业
    public void ofDevCenterAndDirectDeptMustEntp(Long superior, String orgType, OrgType superiorOrgType) {
        if (("DEVCENT".equals(orgType) || "DIRDEP".equals(orgType))
                && !"ENTP".equals(superiorOrgType.getCode())) {
            throw new BusinessException("开发中心或直属部门的上级(id = '" + superior
                    + "')不是企业！");
        }
    }

    // 开发组的上级只能是开发中心
    public void ofDevGroupMustDevCenter(Long superior, String orgType, OrgType superiorOrgType) {
        if ("DEVGRP".equals(orgType) && !"DEVCENT".equals(superiorOrgType.getCode())) {
            throw new BusinessException("开发组的上级(id = '" + superior
                    + "')不是开发中心！");
        }
    }
}
