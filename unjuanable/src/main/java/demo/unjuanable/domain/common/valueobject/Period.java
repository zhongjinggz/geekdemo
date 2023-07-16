package demo.unjuanable.domain.common.valueobject;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate start;
    private LocalDate end;

    private Period(LocalDate start, LocalDate end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("开始和结束日期不能为空，且结束日期不能小于开始日期！");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(start, period.start) && Objects.equals(end, period.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public static Period of(LocalDate start, LocalDate end){
        return new Period(start, end);
    }

    public static Period of(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        return new Period(LocalDate.of(startYear, startMonth, startDay), LocalDate.of(endYear, endMonth, endDay));
    }

    public boolean overlap(Period other) {
        if (other == null) {
            throw new IllegalArgumentException("入参不能为空！");
        }
        return other.start.isBefore(this.end) && other.end.isAfter(this.start);
    }

    public Period merge(Period other) {
        LocalDate newStart = this.start.isBefore(other.start) ? this.start : other.start;
        LocalDate newEnd = this.end.isAfter(other.end) ? this.end : other.end;
        return new Period(newStart, newEnd);
    }
    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + " ~ " + end ;
    }
}
