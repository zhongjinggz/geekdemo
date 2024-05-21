package demo.unjuanable.domain.orgmng.orgtype;

import demo.unjuanable.domain.common.exception.BusinessException;

import java.util.Arrays;

public enum OrgTypeStatus {
    EFFECTIVE("EF", "有效"),
    TERMINATED("TE", "终止");

    private final String code;
    private final String desc;

    public static OrgTypeStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter( s -> s.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException(code + "不是有效的组织类型状态代码！"));
    }

    OrgTypeStatus(String code, String desc) {
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
