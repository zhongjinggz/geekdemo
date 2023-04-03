package demo.unjuanable.domain.tenantmng;

public enum TenantStatus {
    EFFECTIVE("EF", "有效"),
    TERMINATED("EF", "终止");

    private final String code;
    private final String desc;

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
