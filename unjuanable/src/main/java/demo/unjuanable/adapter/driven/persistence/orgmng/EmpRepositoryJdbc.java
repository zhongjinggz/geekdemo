package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import demo.unjuanable.domain.orgmng.emp.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmpRepositoryJdbc implements EmpRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean save(Emp emp) {
        return false;
    }

    @Override
    public Optional<Emp> findById(Long tenantId, Long id) {
        Optional<RebuiltEmp> empMaybe = retrieveEmp(tenantId, id);
        if (empMaybe.isPresent()) {
            RebuiltEmp emp = empMaybe.get();
            retrieveSkills(emp);
            retrieveExperiences(emp);
            retrievePosts(emp);
            return Optional.of(emp);
        } else {
            return Optional.empty();
        }
    }

    private Optional<RebuiltEmp> retrieveEmp(Long tenantId, Long id) {
        String sql = " select org_id, emp_num, id_num, name, gender_code, dob, status_code "
                + " from emp "
                + " where id = ? and tenant_id = ? ";

//        RebuiltEmp emp = jdbc.queryForObject(sql,
//                (rs, rowNum) -> {
//
//                    RebuiltEmp newEmp = new RebuiltEmp(tenantId
//                            , id
//                            , rs.getTimestamp("create_at").toLocalDateTime()
//                            , rs.getLong("created_by"));
//
//                    newEmp.resetVersion(rs.getLong("version"))
//                            .resetOrgId(rs.getLong("org_id"))
//                            .resetNum(rs.getString("num"))
//                            .resetIdNum(rs.getString("id_num"))
//                            .resetName(rs.getString("name"))
//                            .resetGender(Gender.ofCode(rs.getString("gender_code")))
//                            .resetDob(rs.getDate("dob").toLocalDate())
//                            .resetStatus(EmpStatus.ofCode(rs.getString("status_code")));
//                    return newEmp;
//                },
//                id, tenantId);
        // use queryForList instead of queryForObject
        List<Map<String, Object>> rows = jdbc.queryForList(sql, id, tenantId);

        if (rows.isEmpty()) {
            return Optional.empty();
        }

        Map<String, Object> row = rows.get(0);


        RebuiltEmp emp = new RebuiltEmp(tenantId
                , id
                , ((java.sql.Timestamp) row.get("create_at")).toLocalDateTime()
                , (Long) row.get("created_by")
        )
                .resetOrgId((Long) row.get("org_id"))
                .resetNum((String) row.get("num"))
                .resetIdNum((String) row.get("id_num"))
                .resetName((String) row.get("name"))
                .resetGender(Gender.ofCode((String)row.get("gender_code")))
                .resetDob(((java.sql.Date)row.get("dob")).toLocalDate())
                .resetStatus(EmpStatus.ofCode((String)row.get("status_code")));


        return Optional.of(emp);
    }

    private void retrieveSkills(RebuiltEmp emp) {
        //TODO

    }

    private void retrieveExperiences(RebuiltEmp emp) {
        //TODO
    }

    private void retrievePosts(RebuiltEmp emp) {
        //TODO
    }

    @Override
    public boolean existsByIdAndStatus(Long tenantId, Long id, EmpStatus... statuses) {

        String sql = buildSqlExistsByIdAndStatus(statuses.length);

        Object[] params = buildParamsExistsByIdAndStatus(tenantId, id, statuses);


        try {
            return jdbc.queryForObject(sql, Integer.class, params) != null;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    private static Object[] buildParamsExistsByIdAndStatus(Long tenantId, Long id, EmpStatus[] statuses) {
        Object[] params = new Object[2 + statuses.length];
        params[0] = tenantId;
        params[1] = id;
        int j = 2;
        for (EmpStatus status : statuses) {
            params[j++] = status.code();
        }
        return params;
    }

    private static String buildSqlExistsByIdAndStatus(int statusCount) {
        StringBuilder status_condition = new StringBuilder();
        for (int i = 0; i < statusCount; i++) {
            if (i == 0) {
                status_condition.append(" and (status_code = ?");
            } else {
                status_condition.append("or status_code = ?");
            }
        }
        status_condition.append(")");

        return " select 1 from emp  where tenant_id = ? and id = ?"
                + status_condition
                + " limit 1 ";
    }
}
