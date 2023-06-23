package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.list;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EmpServiceIT {
    private static final long DEFAULT_USER_ID = 1L;
    private static final long DEFAULT_TENANT_ID = 1L;

    private static final long JAVA_TYPE_ID = 1L;
    public static final String JAVA_LEVEL_CODE = "MED";
    public static final int JAVA_DURATION = 3;

    public static final long PYTHON_TYPE_ID = 2L;
    public static final String PYTHON_LEVEL_CODE = "ADV";
    public static final int PYTHON_DURATION = 10;

    public static final long CPP_TYPE_ID = 3L;
    public static final String CPP_LEVEL_CODE = "BEG";
    public static final int CPP_DURATION = 1;

    public static final long GOLANG_TYPE_ID = 4L;
    public static final String GOLANG_LEVEL_CODE = "MED";
    public static final int GOLANG_DURATION = 4;

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
                        tuple(JAVA_TYPE_ID, SkillLevel.MEDIUM, JAVA_DURATION),
                        tuple(PYTHON_TYPE_ID, SkillLevel.ADVANCED, PYTHON_DURATION),
                        tuple(CPP_TYPE_ID, SkillLevel.BEGINNER, CPP_DURATION)
                );

    }

    @Test
    void updateEmp_shouldSuccess_WhenUpdateEmpName_AndRemovePythonSkill_AndAddGolangSkill_AndUpdateCppSkill() {
        // given
        Emp origionEmp = prepareEmpInDb();

        // when
        UpdateEmpRequest updateRequest = buildUpdateEmpRequest()
                .setEmpNum(origionEmp.getEmpNum())
                .setName("Dunne")
                .removeSkill(PYTHON_TYPE_ID)
                .addSkill(GOLANG_TYPE_ID, GOLANG_LEVEL_CODE, GOLANG_DURATION)
                .updateSkill(CPP_TYPE_ID, CPP_LEVEL_CODE, CPP_DURATION + 1);

        empService.updateEmp(origionEmp.getId(), updateRequest, origionEmp.getTenantId());

        // then
        Emp actual = empRepository.findById(origionEmp.getTenantId(), origionEmp.getId())
                .orElseGet(() -> fail("找不到刚刚修改的员工！"));

        RebuiltEmp expected = new RebuiltEmp(origionEmp.getTenantId()
                , origionEmp.getId()
                , LocalDateTime.now()
                , origionEmp.getCreatedBy()
        )
                .resetStatus(origionEmp.getStatus())
                .resetOrgId(origionEmp.getOrgId())
                .resetDob(updateRequest.getDob())
                .resetEmpNum(updateRequest.getEmpNum())
                .resetGender(Gender.ofCode(updateRequest.getGenderCode()))
                .resetName(updateRequest.getName())
                .resetIdNum(updateRequest.getIdNum());

        updateRequest.getSkills().forEach(skill ->
                        expected.reAddSkill(
                                skill.getId()
                                , skill.getSkillTypeId()
                                , SkillLevel.ofCode(skill.getLevelCode())
                                , skill.getDuration()
                                , DEFAULT_USER_ID
                        )
        );

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("skills"
                        , "createdAt"
                        , "createdBy"
                        , "updatedAt"
                        , "updatedBy"
                        , "version")
                .isEqualTo(expected);

        assertThat(actual.getSkills()).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .comparingOnlyFields("tenantId", "empId", "skillTypeId", "level", "duration")
                .isEqualTo(expected.getSkills());

//                .comparingOnlyFields("tenantId","id", "empId", "skillTypeId", "level", "duration")
    }

    private Emp prepareEmpInDb() {
        CreateEmpRequest createRequest = buildCreateEmpRequest();
        EmpResponse response = empService.addEmp(createRequest, DEFAULT_TENANT_ID);
        return empRepository.findById(response.getTenantId(), response.getId())
                .orElseGet(() -> fail("找不到新增的员工！"));
    }

    private CreateEmpRequest buildCreateEmpRequest() {
        final long DEFAULT_ORG_ID = 1L;
        final String DEFAULT_EMP_STATUS_CODE = EmpStatus.REGULAR.code();

        CreateEmpRequest result = new CreateEmpRequest();
        return ((CreateEmpRequest) buildBaseEmpRequest(result))
                .setOrgId(DEFAULT_ORG_ID)
                .setStatusCode(DEFAULT_EMP_STATUS_CODE);
    }

    private UpdateEmpRequest buildUpdateEmpRequest() {
        UpdateEmpRequest result = new UpdateEmpRequest();
        return (UpdateEmpRequest) buildBaseEmpRequest(result);

    }

    private BaseEmpRequest buildBaseEmpRequest(BaseEmpRequest result) {
        final String DEFAULT_EMP_NAME = "Kline";
        final LocalDate DEFAULT_DOB = LocalDate.of(1980, 1, 1);
        final String DEFAULT_GENDER_CODE = Gender.MALE.code();
        final String DEFAULT_ID_NUM = "123456789012345678";

        return result.setTenantId(DEFAULT_TENANT_ID)
                .setIdNum(DEFAULT_ID_NUM)
                .setName(DEFAULT_EMP_NAME)
                .setGenderCode(DEFAULT_GENDER_CODE)
                .setDob(DEFAULT_DOB)
                .addSkill(JAVA_TYPE_ID, JAVA_LEVEL_CODE, JAVA_DURATION)
                .addSkill(PYTHON_TYPE_ID, PYTHON_LEVEL_CODE, PYTHON_DURATION)
                .addSkill(CPP_TYPE_ID, CPP_LEVEL_CODE, CPP_DURATION);
    }




}