package demo.unjuanable.domain.tenantmng;

import demo.unjuanable.common.framework.domain.CodeEnum;

public enum TenantStatus implements CodeEnum {
    EFFECTIVE("EF", "有效"),
    TERMINATED("TERMINATED", "终止");

    private final String code;
    private final String desc;

    public static TenantStatus ofCode(String code) {
        return CodeEnum.ofCode(values(), TenantStatus.class, code);
    }

    TenantStatus(String code, String desc) {
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
