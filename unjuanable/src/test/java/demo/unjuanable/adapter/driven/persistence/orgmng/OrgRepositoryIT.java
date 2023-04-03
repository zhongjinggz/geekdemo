package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrgRepositoryIT {
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
}