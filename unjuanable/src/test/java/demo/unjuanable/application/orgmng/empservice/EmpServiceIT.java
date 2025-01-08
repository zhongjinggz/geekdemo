package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.common.valueobject.Period;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class EmpServiceIT {
    private static final long DEFAULT_USER_ID = 1L;
    private static final long DEFAULT_TENANT_ID = 1L;
    private static final long DEFAULT_ORG_ID = 1L;

    // Default values for emp - begin
    private static final String DEFAULT_EMP_STATUS_CODE = EmpStatus.REGULAR.code();
    private static final String DEFAULT_EMP_NAME = "Kline";
    private static final LocalDate DEFAULT_DOB = LocalDate.of(1980, 1, 1);
    private static final String DEFAULT_GENDER_CODE = Gender.MALE.code();
    private static final String DEFAULT_ID_NUM = "123456789012345678";
    // Default values for emp - end

    // Default values for skill - begin
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
    // Default values for skill - end

    // Default values for experience - begin
    private static final LocalDate CMB_START_DATE = LocalDate.of(2001, 1, 1);
    private static final LocalDate CMB_END_DATE = LocalDate.of(2003, 1, 1);
    private static final String CMB_NAME = "CMB";
    private static final LocalDate PICC_START_DATE = LocalDate.of(2003, 1, 1);
    private static final LocalDate PICC_END_DATE = LocalDate.of(2005, 1, 1);
    private static final String PICC_NAME = "PICC";
    private static final LocalDate AIA_START_DATE = LocalDate.of(2005, 1, 1);
    private static final LocalDate AIA_END_DATE = LocalDate.of(2007, 1, 1);
    private static final String AIA_NAME = "AIA";
    private static final LocalDate ANXIN_START_DATE = LocalDate.of(2007, 1, 1);
    private static final LocalDate ANXIN_END_DATE = LocalDate.of(2009, 1, 1);
    private static final String ANXIN_NAME = "ANXIN";
    // Default values for experience - end


    @Autowired
    private EmpService empService;

    @Autowired
    private EmpRepository empRepository;

    @Test
    void addEmp_shouldAddEmpWithSkillsAndExperiences() {
        // given
        CreateEmpRequest request = buildCreateEmpRequest();

        // when
        EmpResponse empResponse = empService.addEmp(request, DEFAULT_USER_ID);

        // then
        Emp actual = empRepository.findById(DEFAULT_TENANT_ID, empResponse.getId())
                .orElseGet(() -> fail("找不到新增的员工！"));

        Emp expected = buildExpectedCreatedEmp(request, empResponse.getId());

        assertEmp(actual, expected);
    }

    @Test
    void updateEmp_shouldUpdateEmpName_RemoveAddUpdateSkillsAndExperiences() {
        // given
        Emp origionEmp = prepareEmpInDb();

        // when
        UpdateEmpRequest updateRequest = buildUpdateEmpRequest(origionEmp);
        empService.updateEmp(origionEmp.getId(), updateRequest, origionEmp.getTenantId());

        // then
        Emp actual = empRepository.findById(origionEmp.getTenantId(), origionEmp.getId())
                .orElseGet(() -> fail("找不到刚刚修改的员工！"));
        Emp expected = buildExpectedUpdatedEmp(origionEmp, updateRequest);

        assertEmp(actual, expected);
    }

    private CreateEmpRequest buildCreateEmpRequest() {

        return new CreateEmpRequest()
                .setTenantId(DEFAULT_TENANT_ID)
                .setIdNum(DEFAULT_ID_NUM)
                .setName(DEFAULT_EMP_NAME)
                .setGenderCode(DEFAULT_GENDER_CODE)
                .setDob(DEFAULT_DOB)
                .setOrgId(DEFAULT_ORG_ID)
                .setStatusCode(DEFAULT_EMP_STATUS_CODE)
                .addSkill(JAVA_TYPE_ID, JAVA_LEVEL_CODE, JAVA_DURATION)
                .addSkill(PYTHON_TYPE_ID, PYTHON_LEVEL_CODE, PYTHON_DURATION)
                .addSkill(CPP_TYPE_ID, CPP_LEVEL_CODE, CPP_DURATION)
                .addExperience(CMB_START_DATE, CMB_END_DATE, CMB_NAME)
                .addExperience(PICC_START_DATE, PICC_END_DATE, PICC_NAME)
                .addExperience(AIA_START_DATE, AIA_END_DATE, AIA_NAME);
    }

    private UpdateEmpRequest buildUpdateEmpRequest(Emp origin) {
        UpdateEmpRequest result = emp2UpdateRequest(origin);
        result.setName("Dunne");

        result.removeSkill(PYTHON_TYPE_ID)
                .addSkill(GOLANG_TYPE_ID, GOLANG_LEVEL_CODE, GOLANG_DURATION)
                .updateSkill(CPP_TYPE_ID, CPP_LEVEL_CODE, CPP_DURATION + 1);

        result.removeExperience(PICC_START_DATE, PICC_END_DATE)
                .addExperience(ANXIN_START_DATE, ANXIN_END_DATE, ANXIN_NAME)
                .updateExperience(CMB_START_DATE, CMB_END_DATE, CMB_NAME + "1");

        return result;


    }

    private Emp buildExpectedCreatedEmp(CreateEmpRequest request, Long id) {
//        RebuiltEmp result = new RebuiltEmp(request.getTenantId()
//                , id
//                , LocalDateTime.now()
//                , DEFAULT_USER_ID
//        )
//                .resetStatus(EmpStatus.ofCode(DEFAULT_EMP_STATUS_CODE))
//                .resetOrgId(request.getOrgId())
//                .resetDob(request.getDob())
//                .resetGender(Gender.ofCode(request.getGenderCode()))
//                .resetName(request.getName())
//                .resetIdNum(request.getIdNum());

//        request.getSkills().forEach(
//                skill -> result.reAddSkill(
//                        skill.getId()
//                        , skill.getSkillTypeId()
//                        , SkillLevel.ofCode(skill.getLevelCode())
//                        , skill.getDuration()
//                        , DEFAULT_USER_ID
//                )
//        );

        List<Skill> skillList = new ArrayList<>();
        for (var dto : request.getSkills()) {
            skillList.add(new Skill(dto.getId()
                            , request.getTenantId()
                            , dto.getSkillTypeId()
                            , dto.getLevelCode()
                            , dto.getDuration()
                            , DEFAULT_USER_ID
                            , LocalDateTime.now()
                            , null
                            , null
                    )
            );
        }

//        request.getExperiences().forEach(
//                experience -> result.reAddExperience(
//                        experience.getId()
//                        ,
//                        Period.of(experience.getStartDate(), experience.getEndDate()), experience.getCompany()
//                        , DEFAULT_USER_ID
//                )
//        );
        List<WorkExperience> experienceMaps = new ArrayList<>();
        for (var dto : request.getExperiences()) {
            experienceMaps.add(new WorkExperience(
                    request.getTenantId()
                    , dto.getId()
                    , dto.getStartDate()
                    , dto.getEndDate()
                    , dto.getCompany()
                    , LocalDateTime.now()
                    , DEFAULT_USER_ID
                    , null
                    , null)
            );
        }

//        RebuiltEmp result = new RebuiltEmp(request.getTenantId()
//                , id
//                , LocalDateTime.now()
//                , DEFAULT_USER_ID
//        )
//                .resetStatus(EmpStatus.ofCode(DEFAULT_EMP_STATUS_CODE))
//                .resetOrgId(request.getOrgId())
//                .resetDob(request.getDob())
//                .resetGender(Gender.ofCode(request.getGenderCode()))
//                .resetName(request.getName())
//                .resetIdNum(request.getIdNum());

        return new Emp(
                request.getTenantId(),
                id,
                request.getOrgId(),
                null,
                request.getIdNum(),
                request.getName(),
                request.getGenderCode(),
                request.getDob(),
                EmpStatus.REGULAR.code(),
                0L,
                LocalDateTime.now(),
                DEFAULT_USER_ID,
                null,
                null,
                skillList,
                experienceMaps,
                new ArrayList<>()
        );

    }

    private Emp buildExpectedUpdatedEmp(Emp origionEmp, UpdateEmpRequest updateRequest) {
        List<Skill> skillList = new ArrayList<>();
        for (SkillDto dto : updateRequest.getSkills()) {
            skillList.add(new Skill(dto.getId()
                            , origionEmp.getTenantId()
                            , dto.getSkillTypeId()
                            , dto.getLevelCode()
                            , dto.getDuration()
                            , origionEmp.getCreatedBy()
                            , origionEmp.getCreatedAt()
                            , origionEmp.getLastUpdatedBy()
                            , origionEmp.getLastUpdatedAt()
                    )
            );
        }

        List<WorkExperience> experienceMaps = new ArrayList<>();
        for (WorkExperienceDto dto : updateRequest.getExperiences()) {
            experienceMaps.add(new WorkExperience(
                    origionEmp.getTenantId()
                    , dto.getId()
                    , dto.getStartDate()
                    , dto.getEndDate()
                    , dto.getCompany()
                    , origionEmp.getCreatedAt()
                    , origionEmp.getCreatedBy()
                    , origionEmp.getLastUpdatedAt()
                    , origionEmp.getLastUpdatedBy())
            );
        }

        List<EmpPost> empPostMaps = new ArrayList<>();
        for (String postCode : updateRequest.getPostCodes()) {
            empPostMaps.add(new EmpPost(
                    postCode
                    , origionEmp.getCreatedAt()
                    , origionEmp.getCreatedBy()
                    , origionEmp.getLastUpdatedAt()
                    , origionEmp.getLastUpdatedBy())
            );
        }

        return new Emp(
                origionEmp.getTenantId(),
                origionEmp.getId(),
                origionEmp.getOrgId(),
                updateRequest.getEmpNum(),
                updateRequest.getIdNum(),
                updateRequest.getName(),
                updateRequest.getGenderCode(),
                updateRequest.getDob(),
                origionEmp.getStatus().code(),
                origionEmp.getVersion(),
                origionEmp.getCreatedAt(),
                origionEmp.getCreatedBy(),
                origionEmp.getLastUpdatedAt(),
                origionEmp.getLastUpdatedBy(),
                skillList,
                experienceMaps,
                empPostMaps
        );
    }

    private void assertEmp(Emp actual, Emp expected) {
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("skills"
                        , "experiences"
                        , "empNum"
                        , "createdAt"
                        , "createdBy"
                        , "updatedAt"
                        , "updatedBy"
                        , "version")
                .isEqualTo(expected);

        assertThat(actual.getSkills()).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringExpectedNullFields() // this is because id of the new skill is null in request
                .comparingOnlyFields("id", "tenantId", "skillTypeId", "level", "duration")
                .isEqualTo(expected.getSkills());

        assertThat(actual.getExperiences()).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringExpectedNullFields() // this is because id of the new experience is null in request
                //.comparingOnlyFields("id", "tenantId", "period", "company")
                .comparingOnlyFields("id", "tenantId", "period", "company")
                .isEqualTo(expected.getExperiences());
    }

    private Emp prepareEmpInDb() {
        CreateEmpRequest createRequest = buildCreateEmpRequest();
        EmpResponse response = empService.addEmp(createRequest, DEFAULT_TENANT_ID);
        return empRepository.findById(response.getTenantId(), response.getId())
                .orElseGet(() -> fail("找不到新增的员工！"));
    }

    private UpdateEmpRequest emp2UpdateRequest(Emp origin) {
        UpdateEmpRequest result = new UpdateEmpRequest();

        result.setTenantId(origin.getTenantId())
                .setIdNum(origin.getIdNum())
                .setName(origin.getName())
                .setGenderCode(origin.getGender().code())
                .setDob(origin.getDob())
                .setEmpNum(origin.getEmpNum());

        origin.getSkills().forEach(skill ->
                result.addSkill(skill.getId()
                        , skill.getSkillTypeId()
                        , skill.getLevel().code()
                        , skill.getDuration()
                )
        );

        origin.getExperiences().forEach(experience ->
                result.addExperience(experience.getPeriod().getStart()
                        , experience.getPeriod().getEnd()
                        , experience.getCompany()
                )
        );
        return result;
    }
}