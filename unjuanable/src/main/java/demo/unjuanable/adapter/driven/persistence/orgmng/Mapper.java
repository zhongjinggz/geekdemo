package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.Persistent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Mapper<T extends Persistent> {

    protected final JdbcTemplate jdbc;
    protected final SimpleJdbcInsert jdbcInsert;

    public Mapper(JdbcTemplate jdbc, String tableName, String... generatedKeyColumns) {
        this.jdbc = jdbc;
        this.jdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName(tableName);
        if (generatedKeyColumns.length > 0) {
            this.jdbcInsert.usingGeneratedKeyColumns(generatedKeyColumns);
        }
    }

    public T save(T theObject) {
        switch (theObject.getChangingStatus()) {
            case NEW:
                insert(theObject);
                saveSubsidiaries(theObject);
                break;
            case UPDATED:
                update(theObject);
                saveSubsidiaries(theObject);
                break;
            case DELETED:
                removeSubsidiaries(theObject);
                delete(theObject);
                break;
        }
        return theObject;
    }

    public void remove(T theObject) {
        removeSubsidiaries(theObject);
        delete(theObject);
    }

    protected void insert(T theObject) {
    }

    protected void update(T theObject) {
    }

    protected void delete(T theObject) {
    }

    protected void saveSubsidiaries(T theObject) {
    }

    protected void removeSubsidiaries(T theObject) {
    }

    protected boolean selectExists(String sql, Object... args) {
        return !(jdbc.queryForList(sql, args).isEmpty());
    }

    protected Optional<T> selectOne(String sql, Function<Map<String, Object>, T> toObj, Object... args) {

        List<T> objList = selectList(sql, toObj, args);
        return objList.isEmpty() ? Optional.empty() : Optional.of(objList.getFirst());
    }

    protected List<T> selectList(String sql
            , Function<Map<String, Object>, T> toObj, Object... args) {

        List<Map<String, Object>> maps = jdbc.queryForList(sql, args);
        return maps.stream().map(toObj).toList();
    }
}
