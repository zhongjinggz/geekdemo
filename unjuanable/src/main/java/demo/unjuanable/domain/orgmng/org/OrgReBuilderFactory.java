package demo.unjuanable.domain.orgmng.org;

import org.springframework.stereotype.Component;

@Component
public class OrgReBuilderFactory {

    public OrgReBuilder build() {
        return new OrgReBuilder();
    }

}
