package demo.unjuanable.domain.orgmng.emp;



import demo.unjuanable.common.framework.domain.WithCode;
import demo.unjuanable.common.framework.exception.BusinessException;

import java.util.Arrays;

public enum Gender implements WithCode {
    MALE("M", "男"),
    FEMALE("F", "女");

    private final String code;
    private final String desc;

    public static Gender ofCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException("性别代码错误！"));
    }

    Gender(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
