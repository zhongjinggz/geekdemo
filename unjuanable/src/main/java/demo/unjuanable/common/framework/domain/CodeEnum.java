package demo.unjuanable.common.framework.domain;

public interface CodeEnum {
    String code();

    // 根据 code 获取枚举
    static <T extends CodeEnum> T ofCode(T[] values, Class<T> enumClass, String code) {
        for (T value : values) {
            if (value.code().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(enumClass.getName() + ": 错误的枚举代码 " + code);
    }
}
