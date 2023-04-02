package demo.unjuanable.adapter.driven.persistence.tenantmng;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TenantRepositoryIT {
    private final TenantRepository tenantRepository;

    @Autowired
    TenantRepositoryIT(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Test
    void should_be_one_for_exists_tenant() {
        assertEquals(1,tenantRepository.countById(1));
    }

    @Test
    void should_be_zero_for_invalid_tenant() {
        assertEquals(0,tenantRepository.countById(0));
    }

}