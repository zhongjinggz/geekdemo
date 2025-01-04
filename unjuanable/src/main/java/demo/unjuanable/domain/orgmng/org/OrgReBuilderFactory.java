package demo.unjuanable.domain.orgmng.org;

import org.springframework.stereotype.Component;

@Component
public class OrgReBuilderFactory {

    public OrgReBuilder newBuilder() {
        return new OrgReBuilder();
    }

}
