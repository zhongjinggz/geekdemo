-- org_type
insert into org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "ENTP","企业", "EFF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DEVCENT","开发中心","EFF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DEVGRP","开发组","EFF", now(), 1);

insert into unjuanable.org_type (tenant_id, code, name, status_code, created_at, created_by )
values (1, "DIRDEP","直属部门", "EFF", now(), 1);


-- emp
insert into emp (tenant_id, org_id, emp_num, id_num, name, gender, dob, status_code, created_at, created_by)
values (1, 1, "20020001","440101197001011234","宋江","M", "1970-01-01", "REG", now(), 1);


-- org
insert into org (tenant_id, org_type_code, superior_id, name, status_code, leader_id, created_at, created_by)
values (1, "ENTP", -1, "梁山科技有限公司", "EFF", 1, now(), 1);


