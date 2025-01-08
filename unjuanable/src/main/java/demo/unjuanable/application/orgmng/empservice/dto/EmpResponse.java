package demo.unjuanable.application.orgmng.empservice.dto;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpPost;

import java.time.LocalDate;
import java.util.List;

public class EmpResponse {
    private Long id;
    private Long tenantId;
    private Long orgId;
    private String empNum;
    private String idNum;

    private String name;
    private String genderCode;
    private LocalDate dob;
    private String statusCode;
    private List<SkillDto> skills;
    private List<WorkExperienceDto> experiences;
    private List<String> postCodes;

    public EmpResponse(Emp emp) {

        this.id = emp.getId();
        this.tenantId = emp.getTenantId();
        this.orgId = emp.getOrgId();
        this.empNum = emp.getEmpNum();
        this.idNum = emp.getIdNum();
        this.name = emp.getName();
        this.genderCode = emp.getGender().code();
        this.dob = emp.getDob();
        this.statusCode = emp.getStatus().code();

        this.skills = emp.getSkills().stream()
                .filter(s -> !s.isDeleted())
                .map(SkillDto::new).toList();

        this.experiences = emp.getExperiences().stream()
                .filter(e -> !e.isDeleted())
                .map(WorkExperienceDto::new)
                .toList();

        this.postCodes = emp.getEmpPosts().stream()
                .filter(p -> !p.isDeleted())
                .map(EmpPost::getPostCode).toList();
    }


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

    public String getEmpNum() {
        return empNum;
    }

    public EmpResponse setEmpNum(String empNum) {
        this.empNum = empNum;
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

    public String getGenderCode() {
        return genderCode;
    }

    public EmpResponse setGenderCode(String genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public EmpResponse setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public EmpResponse setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
