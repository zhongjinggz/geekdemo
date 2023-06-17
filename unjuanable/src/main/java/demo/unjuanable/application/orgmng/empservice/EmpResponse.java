package demo.unjuanable.application.orgmng.empservice;

import java.time.LocalDate;
import java.util.List;

public class EmpResponse {
    private Long id;
    private Long tenantId;
    private Long orgId;
    private String num;
    private String idNum;

    private String name;
    private String gender;
    private LocalDate dob;
    private String status;
    private List<SkillDto> skills;
    private List<WorkExperienceDto> experiences;
    private List<String> postCodes;

    public Long getId() {
        return id;
    }

    public EmpResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public EmpResponse setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public EmpResponse setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getNum() {
        return num;
    }

    public EmpResponse setNum(String num) {
        this.num = num;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public EmpResponse setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public String getName() {
        return name;
    }

    public EmpResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public EmpResponse setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public EmpResponse setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EmpResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public EmpResponse setSkills(List<SkillDto> skills) {
        this.skills = skills;
        return this;
    }

    public List<WorkExperienceDto> getExperiences() {
        return experiences;
    }

    public EmpResponse setExperiences(List<WorkExperienceDto> experiences) {
        this.experiences = experiences;
        return this;
    }

    public List<String> getPostCodes() {
        return postCodes;
    }

    public EmpResponse setPostCodes(List<String> postCodes) {
        this.postCodes = postCodes;
        return this;
    }
}
