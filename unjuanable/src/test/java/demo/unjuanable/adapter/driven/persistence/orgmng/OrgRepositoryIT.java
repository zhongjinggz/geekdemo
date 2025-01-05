package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrgRepositoryIT {
    static final Long DEFAULT_ORG_ID = 1L;
    static final Long DEFAULT_USER_ID = 1L;
    static final long DEFAULT_TENANT_ID = 1L;
    static final long DEFAULT_EMP_ID = 1L;
    final private OrgRepository orgRepository;

    @Autowired
    OrgRepositoryIT(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    @Test
    void save_successfully() {
        Org org = new Org(1L, "DEVCET", LocalDateTime.now(), 1L);
        org.setName("大名府");
        org.setLeaderId(1L);
        org.setSuperiorId(1L);

        Org created = orgRepository.save(org);

        assertNotNull(created.getId());
    }

    @Test
    void findById_exists() {
        Optional<Org> actualOpt = orgRepository.findById(DEFAULT_TENANT_ID, DEFAULT_ORG_ID);

        assertTrue(actualOpt.isPresent());

        Org actual = actualOpt.get();
        assertEquals(DEFAULT_TENANT_ID, actual.getTenantId());
        assertEquals(DEFAULT_ORG_ID, actual.getId());
        assertEquals(OrgStatus.EFFECTIVE, actual.getStatus());
        assertEquals("ENTP", actual.getOrgTypeCode());
    }
    @Test
    void findById_notFound() {
        Optional<Org> org = orgRepository.findById(1L, -1L);
        assertTrue(org.isEmpty());
    }

    @Test
    void findByIdAndStatus_exists() {
        Optional<Org> actualOpt = orgRepository.findByIdAndStatus(DEFAULT_TENANT_ID, DEFAULT_ORG_ID, OrgStatus.EFFECTIVE);

        assertTrue(actualOpt.isPresent());

        Org actual = actualOpt.get();
        assertEquals(DEFAULT_TENANT_ID, actual.getTenantId());
        assertEquals(DEFAULT_ORG_ID, actual.getId());
        assertEquals(OrgStatus.EFFECTIVE, actual.getStatus());
        assertEquals("ENTP", actual.getOrgTypeCode());
    }

    @Test
    void findByIdAndStatus_notFound() {
        Optional<Org> actualOpt = orgRepository.findByIdAndStatus(-1L, -1L, OrgStatus.EFFECTIVE);
        assertTrue(actualOpt.isEmpty());
    }



    @Test
    void existsBySuperiorIdAndName_shouldBeTrue_whenExists() {
        //given
        Org org = prepareOrg();

        //when
        boolean found = orgRepository.existsBySuperiorIdAndName(
                org.getTenantId(),
                org.getSuperiorId(),
                org.getName());

        assertTrue(found);
    }

    @Test
    void existsBySuperiorIdAndName_shouldBeFalse_whenExists() {

        boolean found = orgRepository.existsBySuperiorIdAndName(
                DEFAULT_TENANT_ID
                , DEFAULT_EMP_ID
                , "某某某");

        assertFalse(found);
    }

    @Test
    void existsByIdAndStatus_shouldBeTrue_whenExists() {
        //given
        Org org = prepareOrg();

        //when
        boolean found = orgRepository.existsByIdAndStatus(
                org.getTenantId(),
                org.getId(),
                org.getStatus());

        assertTrue(found);
    }

    @Test
    void existsByIdAndStatus_shouldBeFalse_whenNotExists() {
        //given
        Org org = prepareOrg();

        //when
        boolean found = orgRepository.existsByIdAndStatus(
                org.getTenantId(),
                -1L,
                org.getStatus());

        assertFalse(found);
    }

    private Org prepareOrg() {
        Org org = Org.loader()
                        .tenantId(DEFAULT_TENANT_ID)
                        .superiorId(DEFAULT_ORG_ID)
                        .orgTypeCode("DEVCENT")
                        .leaderId(DEFAULT_EMP_ID)
                        .name("忠义堂")
                        .statusCode(OrgStatus.EFFECTIVE.code())
                        .createdAt(LocalDateTime.now())
                        .createdBy(DEFAULT_USER_ID)
                        .load();
        orgRepository.save(org);
        return org;
    }
}