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
//@Transactional
class TenantMapperIT {

    @Autowired
    TenantMapper tenantMapper;

    @Test
    void insert_should_success() {
        Tenant tenant = new Tenant(LocalDateTime.now(), 1L);
        tenant.setName("test");
        tenant.setStatus(TenantStatus.EFFECTIVE);
        tenantMapper.insert(tenant);
        assertNotNull(tenant.getId());
    }

    @Test
    void selectById_should_success() {
        Tenant tenant = tenantMapper.selectById(1);
        assertNotNull(tenant);
    }
    @Test
    void electExistByIdAndStatus_should_exist() {
        Integer actual = tenantMapper.selectExistByIdAndStatus(1, "EF");
        assertEquals(1, actual);
    }

    @Test
    void electExistByIdAndStatus_should_not_exist() {
        Integer actual = tenantMapper.selectExistByIdAndStatus(-1, "EF");
        assertNull(actual);
    }


}