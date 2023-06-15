package demo.unjuanable.domain.orgmng.emp;

import java.util.Arrays;
import demo.unjuanable.common.framework.exception.BusinessException;
public enum SkillLevel {
    JUNIOR("JU","初级")
    , MEDIUM("ME", "中级")
    , SENIOR("SE","高级");

    private final String code;
    private final String desc;

    public static SkillLevel ofCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new BusinessException("技能水平代码错误！"));
    }

    SkillLevel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }
}
