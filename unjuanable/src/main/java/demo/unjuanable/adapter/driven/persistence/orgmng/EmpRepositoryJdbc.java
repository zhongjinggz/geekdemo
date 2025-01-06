package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;

@Repository
public class EmpRepositoryJdbc extends RootMapper<Emp> implements EmpRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert empInsert;
    private final SkillMapper skillMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EmpPostMapper empPostMapper;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc
            , SkillMapper skillMapper
            , WorkExperienceMapper workExperienceMapper
            , EmpPostMapper empPostMapper) {

        this.jdbc = jdbc;
        this.empInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("emp")
                .usingGeneratedKeyColumns("id");
        this.skillMapper = skillMapper;
        this.workExperienceMapper = workExperienceMapper;
        this.empPostMapper = empPostMapper;
    }


    @Override
    protected void insert(Emp emp) {

        Map<String, Object> params = new HashMap<>();
        params.put("tenant_id", emp.getTenantId());
        params.put("org_id", emp.getOrgId());
        params.put("emp_num", emp.getEmpNum());
        params.put("id_num", emp.getIdNum());
        params.put("name", emp.getName());
        params.put("gender_code", emp.getGender().code());
        params.put("dob", emp.getDob());
        params.put("status_code", emp.getStatus().code());
        params.put("created_at", emp.getCreatedAt());
        params.put("created_by", emp.getCreatedBy());
        params.put("version", 0);

        Number createdId = empInsert.executeAndReturnKey(params);

        forceSet(emp, "id", createdId.longValue());
    }

    @Override
    protected void update(Emp emp) {
        String sql = "update emp " +
                " set version = version + 1 " +
                ", org_id = ?" +
                ", emp_num = ?" +
                ", id_num =? " +
                ", name = ?" +
                ", gender_code =?" +
                ", dob = ?" +
                ", status_code =?" +
                ", last_updated_at =?" +
                ", last_updated_by =? " +
                " where tenant_id = ? and id = ? and version = ?";
        int affected = jdbc.update(sql
                , emp.getOrgId()
                , emp.getEmpNum()
                , emp.getIdNum()
                , emp.getName()
                , emp.getGender().code()
                , emp.getDob()
                , emp.getStatus().code()
                , emp.getLastUpdatedAt()
                , emp.getLastUpdatedBy()
                , emp.getTenantId()
                , emp.getId()
                , emp.getVersion());

    }

    @Override
    protected void saveSubsidiaries(Emp emp) {
        emp.getSkills().forEach(s -> skillMapper.save(s, emp));
        emp.getExperiences().forEach(e -> workExperienceMapper.save(e, emp));
        emp.getEmpPosts().forEach(p -> empPostMapper.save(p, emp));
    }

    @Override
    public boolean existByIdAndStatus(Long tenantId, Long id, EmpStatus... statuses) {
        String sql = "select 1 from emp " +
                "where tenant_id = ? " +
                "and id = ? ";

        if (statuses.length > 0) {
            StringBuilder orSql = new StringBuilder(sql).append("and (status_code = '' ");
            for (EmpStatus status : statuses) {
                orSql.append("or status_code = '").append(status.code()).append("' ");
            }
            orSql.append(")");
            sql = orSql.toString();
        }

        sql += " limit 1 ";

        return !jdbc.queryForList(sql, Integer.class, tenantId, id).isEmpty();
    }

    @Override
    public Optional<Emp> findById(Long tenantId, Long id) {
        Optional<RebuiltEmp> empMaybe;
        String sql = " select version" +
                ", org_id" +
                ", emp_num" +
                ", id_num" +
                ", name" +
                ", gender_code" +
                ", dob" +
                ", status_code" +
                ", created_at" +
                ", created_by"
                + " from emp "
                + " where id = ? and tenant_id = ? ";

        List<Map<String, Object>> empMaps = jdbc.queryForList(sql, id, tenantId);

        if (empMaps.isEmpty()) {
            return Optional.empty();
        } else {
            Map<String, Object> empMap = empMaps.getFirst();
            RebuiltEmp emp = new RebuiltEmp(tenantId
                    , id
                    , (LocalDateTime) empMap.get("created_at")
                    , (Long) empMap.get("created_by")
            )
                    .resetVersion((Long) empMap.get("version"))
                    .resetOrgId((Long) empMap.get("org_id"))
                    .resetEmpNum((String) empMap.get("emp_num"))
                    .resetIdNum((String) empMap.get("id_num"))
                    .resetName((String) empMap.get("name"))
                    .resetGender(Gender.ofCode((String) empMap.get("gender_code")))
                    .resetDob(((Date) empMap.get("dob")).toLocalDate())
                    .resetStatus(EmpStatus.ofCode((String) empMap.get("status_code")));
            skillMapper.attachTo(emp);
            workExperienceMapper.attachTo(emp);
            empPostMapper.attachTo(emp);
            return Optional.of(emp);
        }
    }

}
