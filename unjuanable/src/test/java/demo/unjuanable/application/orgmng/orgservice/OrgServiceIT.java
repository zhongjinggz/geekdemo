package demo.unjuanable.application.orgmng.orgservice;

import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrgServiceIT {
    static final Long DEFAULT_USER_ID = 1L;
    static final long DEFAULT_TENANT_ID = 1L;
    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRepository orgRepository;

    @Test
    void addOrg_should_create_org_when_validation_passed() {
        // Given
        CreateOrgRequest request = buildCreateRequest();

        // When
        OrgResponse actualResponse = orgService.addOrg(request, DEFAULT_USER_ID);

        // Then
        assertNotNull(actualResponse.getId());
        assertEquals(request.getTenantId(), actualResponse.getTenantId());
        assertEquals(request.getOrgTypeCode(), actualResponse.getOrgTypeCode());
        assertEquals(request.getLeaderId(), actualResponse.getLeaderId());
        assertEquals(request.getSuperiorId(), actualResponse.getSuperiorId());
        assertEquals(request.getName(), actualResponse.getName());

        Org actualSaved = orgRepository.findById(actualResponse.getTenantId(),actualResponse.getId())
                .orElseGet(Assertions::fail);
        assertEquals(request.getTenantId(), actualSaved.getTenantId());
        assertEquals(request.getOrgTypeCode(), actualSaved.getOrgTypeCode());
        assertEquals(request.getLeaderId(), actualSaved.getLeaderId());
        assertEquals(request.getSuperiorId(), actualSaved.getSuperiorId());
        assertEquals(request.getName(), actualSaved.getName());

    }

    private CreateOrgRequest buildCreateRequest() {
        CreateOrgRequest request = new CreateOrgRequest();
        request.setTenant(DEFAULT_TENANT_ID);
        request.setOrgType("DEVCENT");
        request.setLeader(DEFAULT_USER_ID);
        request.setSuperior(1L);
        request.setName("忠义堂");
        return request;
    }

    @Test
    public void updateOrgBasic_should_update_org_when_org_exists() {
        OrgResponse preparedOrg = prepareOrgTobeUpdated();

        UpdateOrgBasicRequest request = buildUpdateRequest();

        OrgResponse actualResponse = orgService.updateOrgBasic(preparedOrg.getId(), request, 1L);

        assertNotNull(actualResponse);
        assertEquals(request.getName(), actualResponse.getName());
        assertEquals(request.getLeaderId(), actualResponse.getLeaderId());

        //获取更新后的Org对象
        Org actualSaved = orgRepository.findById(preparedOrg.getTenantId(), preparedOrg.getId()).orElse(null);

        assertNotNull(actualSaved);
        assertEquals(request.getName(), actualSaved.getName());
        assertEquals(request.getLeaderId(), actualSaved.getLeaderId());

    }

    private UpdateOrgBasicRequest buildUpdateRequest() {
        UpdateOrgBasicRequest request = new UpdateOrgBasicRequest();
        request.setTenantId(DEFAULT_TENANT_ID);
        request.setLeaderId(DEFAULT_USER_ID);
        request.setName("聚义厅");
        return request;
    }

    private OrgResponse prepareOrgTobeUpdated() {
        CreateOrgRequest createOrgRequest = buildCreateRequest();
        OrgResponse addedOrg = orgService.addOrg(createOrgRequest, DEFAULT_USER_ID);
        return addedOrg;
    }


}