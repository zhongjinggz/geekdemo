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
    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRepository orgRepository;

    @Test
    void addOrg() {
        CreateOrgRequest request = new CreateOrgRequest();
        request.setTenant(1L);
        request.setOrgType("DEVCENT");
        request.setLeader(1L);
        request.setSuperior(1L);
        request.setName("忠义堂");

        Long userId = 1L;

        // Act
        OrgResponse actualResponse = orgService.addOrg(request, userId);

        // Assert
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
}