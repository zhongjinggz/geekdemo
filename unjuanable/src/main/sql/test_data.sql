-- tenant
delete from tenant;

insert into tenant (id, name, status_code, created_at, created_by)
values (1, "梁山", "EF", now(), 1);

-- org_type
delete from org_type;

insert into org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "ENTP","企业", "EF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DEVCENT","开发中心","EF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DEVGRP","开发组","EF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DIRDEP","直属部门", "EF", now(), 1);

-- org
delete from org;

insert into org (id, tenant_id, org_type_code, superior_id, name, leader_id, status_code, created_at, created_by)
values (1, 1, "ENTP", -1, "梁山科技有限公司", 1, "EF", now(), 1);

-- emp
delete from emp where 1 = 1;

insert into emp (id, tenant_id, org_id, emp_num, id_num, name, gender_code, dob, status_code, created_at, created_by)
values (1, 1, 1, "20020001","440101197001011234","宋江","M", "1970-01-01", "REG", now(), 1);

-- emp_num_counter
delete from emp_num_counter where 1 = 1;
insert into emp_num_counter (tenant_id, year_num, max_emp_num)
values(1, 2023, 1);

delete from skill_type where 1 = 1;
insert into skill_type (tenant_id, id, name, created_at, created_by)
values (1, 1, "Java", now(), 1);
insert into skill_type (tenant_id, id, name, created_at, created_by)
values (1, 2, "Python", now(), 1);



