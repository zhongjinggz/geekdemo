package demo.unjuanable.domain.orgmng.emp;

import demo.unjuanable.common.framework.domain.AggregateRoot;
import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.common.valueobject.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Emp extends AggregateRoot {
    protected Long id;
    protected Long tenantId;
    protected Long orgId;

    protected String empNum;
    protected String idNum;

    protected String name;
    protected Gender gender;
    protected LocalDate dob;
    protected EmpStatus status;
    // protected List<Skill> skills;
    protected Map<Long, Skill> skills = new HashMap<>();
    // protected List<WorkExperience> experiences;
    protected Map<Period, WorkExperience> experiences = new HashMap<>();

    protected List<EmpPost> empPosts = new ArrayList<>();

    // 用于新建员工
    public Emp(Long tenantId, EmpStatus status, Long createdBy) {
        super(LocalDateTime.now(), createdBy);
        this.tenantId = tenantId;
        this.status = status;
    }

    //用于从数据库重建员工
    public Emp(Long tenantId, Long id, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.id = id;
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public Emp setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getEmpNum() {
        return empNum;
    }

    public Emp setEmpNum(String empNum) {
        this.empNum = empNum;
        return this;
    }

    public String getIdNum() {
        return idNum;
    }

    public Emp setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }


    public String getName() {
        return name;
    }

    public Emp setName(String name) {
        this.name = name;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Emp setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Emp setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public EmpStatus getStatus() {
        return status;
    }

    Emp becomeRegular() {
        status = status.becomeRegular();
        return this;
    }

    Emp terminate() {
        status = status.terminate();
        return this;
    }

    public Collection<Skill> getSkills() {
        // return Collections.unmodifiableList(skills);
        return Collections.unmodifiableCollection(skills.values());

    }

    public Optional<Skill> getSkill(Long skillTypeId) {
        // return skills.stream()
        //         .filter(s -> s.getSkillTypeId().equals(skillTypeId))
        //         .findAny();
        return Optional.ofNullable(skills.get(skillTypeId));
    }

    public void addSkill(Long skillTypeId, SkillLevel level, Integer duration, Long userId) {
        skillTypeShouldNotDuplicated(skillTypeId);

        Skill newSkill = new Skill(tenantId, skillTypeId, userId).setLevel(level).setDuration(duration);

        //skills.add(newSkill);
        skills.put(skillTypeId, newSkill);
    }

    public Emp updateSkill(Long skillTypeId, SkillLevel level, Integer duration, Long userId) {
        Skill theSkill = this.getSkill(skillTypeId).orElseThrow(() -> new IllegalArgumentException("不存在要修改的skillTypeId!"));

        if (!theSkill.getSkillTypeId().equals(skillTypeId)
                || theSkill.getLevel() != level
                || !theSkill.getDuration().equals(duration)) {

            theSkill.setLevel(level).setDuration(duration).setLastUpdatedBy(userId).setLastUpdatedAt(LocalDateTime.now()).toUpdate();
        }
        return this;
    }

    public Emp deleteSkill(Long skillTypeId) {
        this.getSkill(skillTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Skills中不存在要删除的skillTypeId!"))
                .toDelete();
        return this;
    }


    private void skillTypeShouldNotDuplicated(Long newSkillTypeId) {
        // if (skills.stream().anyMatch(s -> s.getSkillTypeId().equals(newSkillTypeId))) {
        if (skills.get(newSkillTypeId) != null) {
            throw new BusinessException("同一技能不能录入两次！");
        }
    }

    //    public List<WorkExperience> getExperiences() {
    //        return Collections.unmodifiableList(experiences);
    //    }
    public Collection<WorkExperience> getExperiences() {
        return Collections.unmodifiableCollection(experiences.values());
    }

    public void addExperience(Period period, String company, Long userId) {
        durationShouldNotOverlap(period);

        WorkExperience newExperience = new WorkExperience(
                tenantId
                , period
                , LocalDateTime.now()
                , userId)
                .setCompany(company);
        //experiences.add(newExperience);
        experiences.put(period, newExperience);
    }

    public Emp updateExperience(LocalDate startDate, LocalDate endDate, String company, Long userId) {
        // TODO ...
        return this;
    }

    public Emp deleteExperience(LocalDate startDate, LocalDate endDate) {
        // TODO ...
        return this;
    }

    private void durationShouldNotOverlap(Period newPeriod) {
        // if (experiences.stream().anyMatch(e -> e.getPeriod().overlap(newPeriod))) {
        if (experiences.values().stream().anyMatch(e -> e.getPeriod().overlap(newPeriod))) {
            throw new BusinessException("工作经验的时间段不能重叠!");
        }
    }


    public List<EmpPost> getEmpPosts() {
        return Collections.unmodifiableList(empPosts);
    }

    public Emp addEmpPost(String postCode, Long userId) {
        EmpPost post = new EmpPost(LocalDateTime.now(), userId);
        post.setPostCode(postCode);
        empPosts.add(post);
        return this;
    }

    public Emp deleteEmpPost(String postCode, Long userId) {
        //TODO ...
        return this;
    }
}
