package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.common.exception.SystemException;
import demo.unjuanable.domain.orgmng.empnumcounter.EmpNumCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmpNumCounterDao {
    private final JdbcTemplate jdbc;

    private final SimpleJdbcInsert insertEmpNumCounter;

    @Autowired
    public EmpNumCounterDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insertEmpNumCounter = new SimpleJdbcInsert(jdbc)
                .withTableName("emp_num_counter");
    }

    public void insert(EmpNumCounter empNumCounter) {
        Map<String, Object> params = new HashMap<>(8);

        params.put("tenant_id", empNumCounter.getTenantId());
        params.put("year_num", empNumCounter.getYearNum());
        params.put("max_emp_num", empNumCounter.getMaxEmpNum());

        insertEmpNumCounter.execute(params);
    }

    public EmpNumCounter selectByPk(Long tenantId, int yearNum) {
        final String sql = " select tenant_id"
                + ", year_num"
                + ", max_emp_num"
                + " from emp_num_counter "
                + " where tenant_id = ?  and year_num = ? ";

        try {
            return jdbc.queryForObject(sql
                    , (rs, rowNum) -> {
                        return new EmpNumCounter(
                                rs.getLong("tenant_id")
                                , rs.getInt("year_num")
                                , rs.getInt("max_emp_num"));
                    }
                    , tenantId
                    , yearNum);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new SystemException("租户ID为'" + tenantId + "'的年份为'" + yearNum + "'的员工编号计数器不存在！");

        }





    }

    public Integer selectMaxNumByYear(Long tenantId, int yearNum) {
        final String increaseSql = " update emp_num_counter "
                + " set max_emp_num = max_emp_num + 1 "
                + " where tenant_id = ?  and year_num = ? ";

        int rowsAffected = jdbc.update(increaseSql, tenantId, yearNum);

        if (rowsAffected == 0) {
            throw new SystemException("租户ID为'" + tenantId + "'的年份为'" + yearNum + "'的员工编号计数器不存在！");
        }

        final String selectSql = " select max_emp_num "
                + " from emp_num_counter "
                + " where tenant_id = ?  and year_num = ? ";

        try {
            return jdbc.queryForObject(selectSql, Integer.class, tenantId, yearNum);
        } catch (DataAccessException e) {
            throw new SystemException("未知数据访问错误", e);
        }
    }
}
