package demo.unjuanable.common.util;

import demo.unjuanable.domain.orgmng.org.Org;

import java.lang.reflect.Field;

public final class ReflectUtil {
    private ReflectUtil() {}

    public static void forceSet(Object theObj, String fieldName, Object fieldValue) {
        try {
            Field idField = Org.class.getDeclaredField(fieldName);
            idField.setAccessible(true);
            idField.set(theObj, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("编程错误！");
        }
    }
}
