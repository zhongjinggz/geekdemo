package demo.unjuanable.application.orgmng.empservice.dto;

import demo.unjuanable.domain.orgmng.emp.Skill;

public class SkillDto {

    Long id;
    private Long skillTypeId;
    String levelCode; // “BEG” - Beginner, "MED" - Medium, "ADV" - Advanced
    private Integer duration;

    public SkillDto(Long skillTypeId, String levelCode, Integer duration) {
        this.skillTypeId = skillTypeId;
        this.levelCode = levelCode;
        this.duration = duration;
    }

    public SkillDto(Long id, Long skillTypeId, String levelCode, Integer duration) {
        this(skillTypeId, levelCode, duration);
        this.id = id;
    }

    public SkillDto(Skill skill) {
        this(skill.getId()
                , skill.getSkillTypeId()
                , skill.getLevel().code()
                , skill.getDuration());
    }

    public Long getId() {
        return id;
    }

    public Long getSkillTypeId() {
        return skillTypeId;
    }

    public void setSkillTypeId(Long skillTypeId) {
        this.skillTypeId = skillTypeId;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
