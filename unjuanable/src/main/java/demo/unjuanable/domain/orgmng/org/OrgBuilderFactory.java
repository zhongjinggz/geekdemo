package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.domain.common.validator.CommonValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgLeaderValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgNameValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgTypeValidator;
import demo.unjuanable.domain.orgmng.org.validator.SuperiorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgBuilderFactory {
    private final CommonValidator commonValidator;
    private final OrgTypeValidator orgTypeValidator;
    private final SuperiorValidator superiorValidator;
    private final OrgNameValidator orgNameValidator;
    private final OrgLeaderValidator orgLeaderValidator;

    @Autowired
    public OrgBuilderFactory(CommonValidator commonValidator
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

    public OrgBuilder create() {
        return new OrgBuilder(commonValidator
                , orgTypeValidator
                , superiorValidator
                , orgNameValidator
                , orgLeaderValidator);
    }
}
