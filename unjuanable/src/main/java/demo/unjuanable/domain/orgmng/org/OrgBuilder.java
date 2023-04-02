package demo.unjuanable.domain.orgmng.org;


import demo.unjuanable.domain.common.validator.CommonValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgLeaderValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgNameValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgTypeValidator;
import demo.unjuanable.domain.orgmng.org.validator.SuperiorValidator;
import demo.unjuanable.domain.orgmng.orgtype.OrgType;

import java.time.LocalDateTime;

public class OrgBuilder {
    private final CommonValidator commonValidator;
    private final OrgTypeValidator orgTypeValidator;
    private final SuperiorValidator superiorValidator;
    private final OrgNameValidator orgNameValidator;
    private final OrgLeaderValidator orgLeaderValidator;

    private Long tenantId;
    private Long superiorId;
    private String orgTypeCode;
    private Long leaderId;
    private String name;
    private Long createdBy;

    OrgBuilder(CommonValidator commonValidator
            , OrgTypeValidator orgTypeValidator
            , SuperiorValidator superiorValidator
            , OrgNameValidator orgNameValidator
            , OrgLeaderValidator orgLeaderValidator) {

        this.commonValidator = commonValidator;
        this.orgTypeValidator = orgTypeValidator;
        this.superiorValidator = superiorValidator;
        this.orgNameValidator = orgNameValidator;
        this.orgLeaderValidator = orgLeaderValidator;
    }

    public OrgBuilder tenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public OrgBuilder superiorId(Long superiorId) {
        this.superiorId = superiorId;
        return this;
    }

    public OrgBuilder orgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
        return this;
    }

    public OrgBuilder leaderId(Long leaderId) {
        this.leaderId = leaderId;
        return this;
    }

    public OrgBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OrgBuilder createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Org build() {
        validate();

        Org org = new Org(tenantId, orgTypeCode, LocalDateTime.now(), createdBy);
        org.setLeaderId(this.leaderId);
        org.setName(this.name);
        org.setSuperiorId(this.superiorId);

        return org;
    }

    private void validate() {

        commonValidator.tenantShouldValid(tenantId);
        orgLeaderValidator.leaderShouldBeEffective(tenantId, leaderId);

        verifyOrgType();
        verifySuperior();
        verifyOrgName();
    }

    private void verifyOrgName() {
        orgNameValidator.orgNameShouldNotEmpty(name);
        orgNameValidator.nameShouldNotDuplicatedInSameSuperior(tenantId, superiorId, name);
    }

    private void verifySuperior() {
        Org superiorOrg = superiorValidator.superiorShouldEffective(tenantId, superiorId);
        OrgType superiorOrgType = superiorValidator.findSuperiorOrgType(tenantId, superiorId, superiorOrg);
        superiorValidator.superiorOfDevGroupMustDevCenter(superiorId, orgTypeCode, superiorOrgType);
        superiorValidator.SuperiorOfDevCenterAndDirectDeptMustEntp(superiorId, orgTypeCode, superiorOrgType);
    }

    private void verifyOrgType() {
        orgTypeValidator.orgTypeShouldNotEmpty(orgTypeCode);
        orgTypeValidator.orgTypeShouldBeValid(tenantId, orgTypeCode);
        orgTypeValidator.shouldNotCreateEntpAlone(orgTypeCode);
    }


}
