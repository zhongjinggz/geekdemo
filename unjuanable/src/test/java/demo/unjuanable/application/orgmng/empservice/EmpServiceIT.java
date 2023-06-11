package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
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
class EmpServiceIT {
    @Autowired
    private EmpService empService;

    @Autowired
    private EmpRepository empRepository;

    // integration test for EmpService.addEmp with SpringBootTest
    // test case: can add emp with valid request
    // steps:
    // 1. prepare request
    // 2. call addEmp
    // 3. select the emp from db
    // 4. assert the emp is added
    // 5. assert the emp is in the correct status
//    @Test
//    void addEmp() {
//        // 1. prepare request
//        CreateEmpRequest request = new CreateEmpRequest();
//        request.setTenantId(1L);
//        request.setOrgId(1L);
//        request.setIdNum("123456789012345678");
//        request.setName("张三");
//        request.setGenderCode("M");
//        request.setDob("1990-01-01");
//        Long userId = 1L;
//
//        // 2. call addEmp
//        EmpResponse actualResponse = empService.addEmp(request, userId);
//        // 3. select the emp from db
//        Emp saved = empRepository.findById(1L, actualResponse.getId()).get();
//
//        // 4. assert the emp is added
//        // 5. assert the emp is in the correct status
//    }


}