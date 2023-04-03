package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.domain.common.exception.BusinessException;

import java.util.Arrays;

public enum OrgStatus {
    EFFECTIVE("EF", "有效"),
    CANCELLED("CA", "终止");

    private final String code;
    private final String desc;

    public static OrgStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter( s -> s.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException(code + "不是有效的组织状态代码！"));
    }

    OrgStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
