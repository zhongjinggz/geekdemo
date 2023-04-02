package demo.unjuanable.application.orgmng.orgservice;

import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
        assertEquals(request.getTenant(), actualResponse.getTenant());
        assertEquals(request.getOrgType(), actualResponse.getOrgType());
        assertEquals(request.getLeader(), actualResponse.getLeader());
        assertEquals(request.getSuperior(), actualResponse.getSuperior());
        assertEquals(request.getName(), actualResponse.getName());
    }
}