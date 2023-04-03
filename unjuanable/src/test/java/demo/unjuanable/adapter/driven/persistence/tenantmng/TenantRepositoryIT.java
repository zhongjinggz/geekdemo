package demo.unjuanable.adapter.driven.persistence.tenantmng;

import demo.unjuanable.domain.tenantmng.Tenant;
import demo.unjuanable.domain.tenantmng.TenantStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TenantRepositoryIT {
    private final TenantRepositoryJdbc tenantRepository;

    @Autowired
    TenantRepositoryIT(TenantRepositoryJdbc tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Test
    public void existsByIdAndStatus_shouldBeTrue_whenExists() {
        //given
        Tenant tenant = prepareTenant();

        //when
        boolean found = tenantRepository.existsByIdAndStatus(
                tenant.getId(),
                tenant.getStatus());

        //then
        assertTrue(found);
    }

    @Test
    public void existsByIdAndStatus_shouldBeFalse_whenNotExists() {
        //given
        Tenant tenant = prepareTenant();

        //when
        boolean found = tenantRepository.existsByIdAndStatus(
                tenant.getId(),
                TenantStatus.TERMINATED);

        //then
        assertFalse(found);
    }

    private Tenant prepareTenant() {
        Tenant result = new Tenant(LocalDateTime.now(), 1L);
        result.setName("某某某");
        tenantRepository.save(result);
        return result;
    }
}