package demo.unjuanable.domain.orgmng.emp;

public enum EmpStatus {
    REGULAR("REG", "正式"),
    PROBATION("PRO", "试用期"),
    TERMINATED("TER", "终止");

    private final String code;
    private final String desc;

    EmpStatus(String code, String desc) {
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
