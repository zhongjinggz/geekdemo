package demo.unjuanable.application.orgmng.empservice;

import java.time.LocalDate;

public class WorkExperienceDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String company;

    public WorkExperienceDto(Long id, LocalDate startDate, LocalDate endDate, String company) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
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
}