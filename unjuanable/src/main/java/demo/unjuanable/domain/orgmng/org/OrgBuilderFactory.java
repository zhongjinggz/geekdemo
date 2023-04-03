package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.domain.common.validator.CommonTenantValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgLeaderValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgNameValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgTypeValidator;
import demo.unjuanable.domain.orgmng.org.validator.SuperiorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgBuilderFactory {
    private final CommonTenantValidator commonTenantValidator;
    private final OrgTypeValidator orgTypeValidator;
    private final SuperiorValidator superiorValidator;
    private final OrgNameValidator orgNameValidator;
    private final OrgLeaderValidator orgLeaderValidator;

    @Autowired
    public OrgBuilderFactory(CommonTenantValidator commonTenantValidator
            , OrgTypeValidator orgTypeValidator
            , SuperiorValidator superiorValidator
            , OrgNameValidator orgNameValidator
            , OrgLeaderValidator orgLeaderValidator) {

        this.commonTenantValidator = commonTenantValidator;
        this.orgTypeValidator = orgTypeValidator;
        this.superiorValidator = superiorValidator;
        this.orgNameValidator = orgNameValidator;
        this.orgLeaderValidator = orgLeaderValidator;
    }

    public OrgBuilder create() {
        return new OrgBuilder(commonTenantValidator
                , orgTypeValidator
                , superiorValidator
                , orgNameValidator
                , orgLeaderValidator);
    }

}
