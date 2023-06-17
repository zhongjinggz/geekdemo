package demo.unjuanable.domain.orgmng.emp;

import java.util.Arrays;
import demo.unjuanable.common.framework.exception.BusinessException;
public enum SkillLevel {
    BEGINNER("BEG","初级")
    , MEDIUM("MED", "中级")
    , ADVANCED("ADV","高级");

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
