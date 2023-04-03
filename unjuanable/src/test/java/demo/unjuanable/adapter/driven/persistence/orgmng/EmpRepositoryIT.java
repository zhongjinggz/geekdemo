package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import demo.unjuanable.domain.orgmng.org.Org;
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
class EmpRepositoryIT {

    @Autowired
    private EmpRepository empRepository;

    @Test
    public void existsByIdAndStatus_shouldBeTrue_whenExists() {

        boolean found = empRepository.existsByIdAndStatus( 1L ,1L ,EmpStatus.REGULAR);

        assertTrue(found);
    }

    @Test
    public void existsByIdAndStatus_shouldBeFalse_whenNotExists() {

        boolean found = empRepository.existsByIdAndStatus( 1L ,1L ,EmpStatus.TERMINATED);

        assertFalse(found);
    }
}