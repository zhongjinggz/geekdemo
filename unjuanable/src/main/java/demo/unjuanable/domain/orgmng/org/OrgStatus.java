package demo.unjuanable.domain.orgmng.org;

import java.util.Arrays;
import java.util.Optional;

public enum OrgStatus {
    EFFECTIVE("EF", "有效"),
    CANCELLED("CA", "终止");

    private final String code;
    private final String desc;

    public static Optional<OrgStatus> ofCode(String code) {
        return Arrays.stream(values()).filter( s -> s.code.equals(code)).findAny();
    }

    OrgStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }
}
