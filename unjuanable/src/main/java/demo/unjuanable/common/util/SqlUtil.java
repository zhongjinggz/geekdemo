package demo.unjuanable.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class SqlUtil {
    private SqlUtil(){}

    public static LocalDateTime toLocalDateTime(ResultSet rs, String fieldName) throws SQLException {
        Timestamp ts = rs.getTimestamp(fieldName);
        return ts == null ? null : ts.toLocalDateTime();
    }

}
