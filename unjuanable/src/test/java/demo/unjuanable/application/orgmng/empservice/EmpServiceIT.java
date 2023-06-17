package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import demo.unjuanable.domain.orgmng.emp.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EmpServiceIT {
    @Autowired
    private EmpService empService;

    @Autowired
    private EmpRepository empRepository;

    @Test
    void addEmp_shouldAddEmp_WhenWithoutSubsidiaries() {
        // given
        CreateEmpRequest request = buildCreateEmpRequest();

        // when
        EmpResponse response = empService.addEmp(request, 1L);

        // then
        Emp savedEmp = empRepository.findById(1L, response.getId())
                .orElseGet(() -> fail("找不到新增的员工！"));

        assertThat(savedEmp).extracting(Emp::getTenantId
                        , Emp::getOrgId
                        , Emp::getName
                        , Emp::getDob
                        , Emp::getIdNum
                        , Emp::getGender)
                .containsExactly(request.getTenantId()
                        , request.getOrgId()
                        , request.getName()
                        , request.getDob()
                        , request.getIdNum()
                        , Gender.ofCode(request.getGenderCode()
                        )
                );
    }

    private CreateEmpRequest buildCreateEmpRequest() {
        CreateEmpRequest request = new CreateEmpRequest();
        request.setTenantId(1L);
        request.setName("emp1");
        request.setOrgId(1L);
        request.setDob(LocalDate.of(1980, 1, 1));
        request.setGenderCode(Gender.MALE.code());
        request.setIdNum("123456789012345678");
        request.setStatusCode(EmpStatus.REGULAR.code());
        return request;
    }

    @Test
    void updateEmp_shouldUpdateEmp_WhenWithoutSubsidiaries() {
        // given
        CreateEmpRequest request = buildCreateEmpRequest();
        EmpResponse response = empService.addEmp(request, 1L);

        // when
        UpdateEmpRequest updateRequest = new UpdateEmpRequest();
        updateRequest.setTenantId(response.getTenantId());
        updateRequest.setIdNum(response.getIdNum());
        updateRequest.setName("emp2");
        updateRequest.setGenderCode(response.getGenderCode());
        updateRequest.setDob(response.getDob());

        empService.updateEmp(response.getId(), updateRequest, 1L);

        // then
        Emp updatedEmp = empRepository.findById(1L, response.getId())
                .orElseGet(() -> fail("找不到刚刚修改的员工！"));

        assertThat(updatedEmp).extracting(Emp::getTenantId
                        , Emp::getOrgId
                        , Emp::getName
                        , Emp::getDob
                        , Emp::getIdNum
                        , Emp::getGender)
                .containsExactly(request.getTenantId()
                        , request.getOrgId()
                        , request.getName()
                        , request.getDob()
                        , request.getIdNum()
                        , Gender.ofCode(request.getGenderCode()
                        )
                );


    }


}