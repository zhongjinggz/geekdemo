package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.domain.common.exception.BusinessException;

import java.util.Arrays;

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

    public static EmpStatus ofCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException("员工状态代码错误！"));

    }
}
