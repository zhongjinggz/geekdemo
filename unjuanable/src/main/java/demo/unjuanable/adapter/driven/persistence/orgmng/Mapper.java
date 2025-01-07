package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.Persistent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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
}
