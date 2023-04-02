package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.domain.common.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrgStatusTest {

    @Test
    void ofCode() {
        assertEquals(OrgStatus.EFFECTIVE, OrgStatus.ofCode("EF"));

        assertEquals(OrgStatus.CANCELLED, OrgStatus.ofCode("CA"));

        assertThrows(BusinessException.class, () -> OrgStatus.ofCode("XX"));
    }
}