package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
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

    @Test
    void findById_notfound() {
        Optional<Org> org = orgRepository.findById(1L, -1L);
        assertTrue(org.isEmpty());
    }
}