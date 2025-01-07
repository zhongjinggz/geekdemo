package demo.unjuanable.adapter.driven.persistence.orgmng.emp;

import demo.unjuanable.common.framework.adapter.driven.persistence.Mapper;
import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static demo.unjuanable.common.util.ReflectUtil.forceSet;
import static demo.unjuanable.common.util.SqlUtil.toLocalDate;

@Repository
public class EmpRepositoryJdbc extends Mapper<Emp> implements EmpRepository {
    private final SkillMapper skillMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EmpPostMapper empPostMapper;

    @Autowired
    public EmpRepositoryJdbc(JdbcTemplate jdbc
            , SkillMapper skillMapper
            , WorkExperienceMapper workExperienceMapper
            , EmpPostMapper empPostMapper) {

        super(jdbc, "emp", "id");
        this.skillMapper = skillMapper;
        this.workExperienceMapper = workExperienceMapper;
        this.empPostMapper = empPostMapper;
    }


    @Override
    protected void insert(Emp emp) {

        Map<String, Object> args = new HashMap<>();
        args.put("tenant_id", emp.getTenantId());
        args.put("org_id", emp.getOrgId());
        args.put("emp_num", emp.getEmpNum());
        args.put("id_num", emp.getIdNum());
        args.put("name", emp.getName());
        args.put("gender_code", emp.getGender().code());
        args.put("dob", emp.getDob());
        args.put("status_code", emp.getStatus().code());
        args.put("created_at", emp.getCreatedAt());
        args.put("created_by", emp.getCreatedBy());
        args.put("version", 0);

        Number createdId = jdbcInsert.executeAndReturnKey(args);

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
        emp.getSkills().forEach(skillMapper::save);
        emp.getExperiences().forEach(workExperienceMapper::save);
        emp.getEmpPosts().forEach(empPostMapper::save);
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

        return selectExists(sql, tenantId, id);
    }

    @Override
    public Optional<Emp> findById(Long tenantId, Long id) {
        String sql = " select version"
                + ", tenant_id"
                + ", id"
                + ", org_id"
                + ", emp_num"
                + ", id_num"
                + ", name"
                + ", gender_code"
                + ", dob"
                + ", status_code"
                + ", version"
                + ", created_at"
                + ", created_by"
                + " from emp "
                + " where id = ? and tenant_id = ? ";

        return selectOne(sql, mapToEmp(), id, tenantId);
    }

    private Function<Map<String, Object>, Emp> mapToEmp() {
        return empMap -> {
            Long tenantId = (Long) empMap.get("tenant_id");
            Long empId = (Long) empMap.get("id");

            List<Skill> skillList = skillMapper.selectByEmpId(tenantId, empId);
            List<WorkExperience> experienceList = workExperienceMapper.selectByEmpId(tenantId, empId);
            List<EmpPost> empPostList = empPostMapper.selectByEmpId(tenantId, empId);


            return new Emp(
                    tenantId
                    , (Long) empMap.get("id")
                    , (Long) empMap.get("org_id")
                    , (String) empMap.get("emp_num")
                    , (String) empMap.get("id_num")
                    , (String) empMap.get("name")
                    , (String) empMap.get("gender_code")
                    , toLocalDate(empMap,"dob")
                    , (String) empMap.get("status_code")
                    , (Long) empMap.get("version")
                    , (LocalDateTime) empMap.get("created_at")
                    , (Long) empMap.get("created_by")
                    , (LocalDateTime) empMap.get("last_updated_at")
                    , (Long) empMap.get("last_updated_by")

                    , skillList
                    , experienceList
                    , empPostList
            );
        };
    }

}
