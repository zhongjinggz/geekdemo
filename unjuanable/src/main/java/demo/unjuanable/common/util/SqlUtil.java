package demo.unjuanable.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public final class SqlUtil {
    private SqlUtil(){}

    public static LocalDateTime toLocalDateTime(ResultSet rs, String fieldName) throws SQLException {
        Timestamp ts = rs.getTimestamp(fieldName);
        return ts == null ? null : ts.toLocalDateTime();
    }

    public static LocalDate toLocalDate(Map<String, Object> map, String fieldName) {
        java.sql.Date dateMaybe = (java.sql.Date) map.get(fieldName);
        return dateMaybe == null ? null : dateMaybe.toLocalDate();
    }

}
