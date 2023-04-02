package demo.unjuanable.common.util;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void isBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));

        assertFalse(StringUtils.isBlank("hello"));
        assertFalse(StringUtils.isBlank("  hello  "));
    }
}

