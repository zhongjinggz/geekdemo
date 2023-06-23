package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EmpServiceIT {
    private static final long DEFAULT_TENANT_ID = 1L;
    private static final long DEFAULT_ORG_ID = 1L;
    private static final long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_EMP_NAME = "Kline";
    private static final LocalDate DEFAULT_DOB = LocalDate.of(1980, 1, 1);
    private static final String DEFAULT_GENDER_CODE = Gender.MALE.code();
    private static final String DEFAULT_ID_NUM = "123456789012345678";
    private static final String DEFAULT_EMP_STATUS_CODE = EmpStatus.REGULAR.code();

    private static final long JAVA_TYPE_ID = 1L;
    public static final String JAVA_LEVEL_CODE = "MED";
    public static final int JAVA_DURATION = 3;

    public static final long PYTHON_TYPE_ID = 2L;
    public static final String PYTHON_TYPE_CODE = "ADV";
    public static final int PYTHON_DURATION = 5;

    @Autowired
    private EmpService empService;

    @Autowired
    private EmpRepository empRepository;

    @Test
    void addEmp_shouldAddEmpWithSkills() {
        // given
        CreateEmpRequest request = buildCreateEmpRequest();

        // when
        EmpResponse empResponse = empService.addEmp(request, DEFAULT_USER_ID);

        // then
        Emp savedEmp = empRepository.findById(DEFAULT_TENANT_ID, empResponse.getId())
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
                        , Gender.ofCode(request.getGenderCode())
                );

        assertThat(savedEmp.getSkills()).extracting("skillTypeId", "level", "duration")
                .containsExactlyInAnyOrder(
                        tuple(DEFAULT_TENANT_ID, SkillLevel.MEDIUM, JAVA_DURATION),
                        tuple(PYTHON_TYPE_ID, SkillLevel.ADVANCED, PYTHON_DURATION)
                );

    }

    @Test
    void updateEmp_shouldUpdateEmpWithoutSubsidiaries() {
        // given
        CreateEmpRequest request = buildCreateEmpRequest();
        EmpResponse response = empService.addEmp(request, DEFAULT_TENANT_ID);

        // when
        UpdateEmpRequest updateRequest = buildUpdateEmpRequest(response).setName("Dunne");

        empService.updateEmp(response.getId(), updateRequest, DEFAULT_TENANT_ID);

        // then
        Emp updatedEmp = empRepository.findById(DEFAULT_TENANT_ID, response.getId())
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

    private CreateEmpRequest buildCreateEmpRequest() {
        return new CreateEmpRequest()
                .setTenantId(DEFAULT_TENANT_ID)
                .setName(DEFAULT_EMP_NAME)
                .setOrgId(DEFAULT_ORG_ID)
                .setDob(DEFAULT_DOB)
                .setGenderCode(DEFAULT_GENDER_CODE)
                .setIdNum(DEFAULT_ID_NUM)
                .setStatusCode(DEFAULT_EMP_STATUS_CODE)
                .addSkill(JAVA_TYPE_ID, JAVA_LEVEL_CODE, JAVA_DURATION)
                .addSkill(PYTHON_TYPE_ID, PYTHON_TYPE_CODE, PYTHON_DURATION);
    }

    private UpdateEmpRequest buildUpdateEmpRequest(EmpResponse response) {
        return new UpdateEmpRequest()
                .setTenantId(response.getTenantId())
                .setIdNum(response.getIdNum())
                .setName(response.getName())
                .setGenderCode(response.getGenderCode())
                .setDob(response.getDob())
                .addSkill(JAVA_TYPE_ID, JAVA_LEVEL_CODE, JAVA_DURATION)
                .addSkill(PYTHON_TYPE_ID, PYTHON_TYPE_CODE, PYTHON_DURATION);
    }


}