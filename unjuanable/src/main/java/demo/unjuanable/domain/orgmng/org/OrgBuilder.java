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

    private Long tenant;
    private Long superior;
    private String orgType;
    private Long leader;
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

    public OrgBuilder tenant(Long tenant) {
        this.tenant = tenant;
        return this;
    }

    public OrgBuilder superior(Long superior) {
        this.superior = superior;
        return this;
    }

    public OrgBuilder orgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public OrgBuilder leader(Long leader) {
        this.leader = leader;
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

        Org org = new Org(tenant, orgType, LocalDateTime.now(), createdBy);
        org.setLeader(this.leader);
        org.setName(this.name);
        org.setSuperior(this.superior);

        return org;
    }

    private void validate() {

        commonValidator.tenantShouldValid(tenant);
        orgLeaderValidator.leaderShouldBeEffective(tenant, leader);

        verifyOrgType();
        verifySuperior();
        verifyOrgName();
    }

    private void verifyOrgName() {
        orgNameValidator.orgNameShouldNotEmpty(name);
        orgNameValidator.nameShouldNotDuplicatedInSameSuperior(tenant, superior, name);
    }

    private void verifySuperior() {
        Org superiorOrg = superiorValidator.superiorShouldEffective(tenant, superior);
        OrgType superiorOrgType = superiorValidator.findSuperiorOrgType(tenant, superior, superiorOrg);
        superiorValidator.superiorOfDevGroupMustDevCenter(superior, orgType, superiorOrgType);
        superiorValidator.SuperiorOfDevCenterAndDirectDeptMustEntp(superior, orgType, superiorOrgType);
    }

    private void verifyOrgType() {
        orgTypeValidator.orgTypeShouldNotEmpty(orgType);
        orgTypeValidator.orgTypeShouldBeValid(tenant, orgType);
        orgTypeValidator.shouldNotCreateEntpAlone(orgType);
    }


}
