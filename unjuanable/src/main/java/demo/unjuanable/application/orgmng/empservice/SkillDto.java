package demo.unjuanable.application.orgmng.empservice;

public class SkillDto {

    Long id;
    private Long skillTypeId;
    String levelCode;
    private int duration;

    public SkillDto(Long id, Long skillTypeId, String levelCode, int duration) {
        this.id = id;
        this.skillTypeId = skillTypeId;
        this.levelCode = levelCode;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
