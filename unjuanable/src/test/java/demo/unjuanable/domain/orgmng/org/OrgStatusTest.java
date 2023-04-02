package demo.unjuanable.domain.orgmng.org;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrgStatusTest {

    @Test
    void ofCode() {
        assertEquals(OrgStatus.EFFECTIVE
                , OrgStatus.ofCode("EF").orElse(null));

        assertEquals(OrgStatus.CANCELLED
                , OrgStatus.ofCode("CA").orElse(null));

        assertNull(OrgStatus.ofCode("XX").orElse(null));
    }
}