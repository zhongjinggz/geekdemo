package demo.unjuanable.application.orgmng.empservice.dto;

import demo.unjuanable.domain.common.valueobject.Period;
import demo.unjuanable.domain.orgmng.emp.WorkExperience;

import java.time.LocalDate;

public class WorkExperienceDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String company;

    public WorkExperienceDto(LocalDate startDate, LocalDate endDate, String company) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
    }

    public WorkExperienceDto(Long id, LocalDate startDate, LocalDate endDate, String company) {
        this(startDate, endDate, company);
        this.id = id;
    }

    public WorkExperienceDto(WorkExperience experience) {
        this(experience.getId()
                , experience.getPeriod().getStart()
                , experience.getPeriod().getEnd()
                , experience.getCompany());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getId() {
        return this.id;
    }

    public Period getPeriod() {
        return Period.of(getStartDate(), getEndDate());
    }
}
