-- Tenant module begin
drop table if exists tenant;
create table tenant
(
    id              int         auto_increment primary key,
    name            varchar(50) not null,
    status          char(2)     not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);
-- Tenant module end

-- Orgmnt module begin
drop table if exists post;
create table post
(
    code            char(10)    primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    name            varchar(50) not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index post__tenant on post (tenant_id);

drop table if exists org_type;
create table org_type
(
    code            char(10)    primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    name            varchar(50) not null,
    status          char(2)     not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index org_type__tenant on org_type (tenant_id);

drop table if exists org;
create table org
(
    id              int         auto_increment primary key,
    tenant_id       int         not null,       -- virtual fk to tenant.id
    superior_id     int,                        -- virtual fk to org.id
    org_type_code   char(10)    not null,       -- virtual fk to org_type.code
    leader_id       int,                        -- virtual fk to emp.id
    name            varchar(50) not null,
    status_code     char(10)    not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index org__tenant on org (tenant_id);
create index org__superior on org (superior_id);
create index org__org_type on org (org_type_code);
create index org__leader on org (leader_id);

drop table if exists emp;
create table emp
(
    id              int         auto_increment primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    org             int,                        -- virtual fk to org.id
    emp_num         varchar(20) not null,
    id_num          varchar(20) not null,
    name            varchar(20) not null,
    gender          varchar(20),
    dob             date,
    status          char(3)     not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index emp__tenant on emp (tenant_id);
create index emp__org on emp (org);

drop table if exists emp_post;
create table emp_post
(
    emp             int,                        -- pk, virtual fk to tenant.id
    post            char(10),                   -- pk, virtual fk to org.id
    tenant_id          int         not null,       -- virtual fk to tenant.id
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int,
    primary key (emp, post)
);

create index emp_post__post on emp_post (post);
-- Orgmnt module end

-- Projectmng module begin
drop table if exists client;
create table client
(
    id              int         auto_increment primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    curr_mng        int         not null,       -- virtual fk to emp.id
    name            varchar(10) not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index client__tenant on client (tenant_id);
create index client__curr_mng on client (curr_mng);

drop table if exists contract;
create table contract
(
    id              int         auto_increment primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    client          int         not null,       -- virtual fk to client.id
    curr_mng        int         not null,       -- virtual fk to emp.id
    num             varchar(50) not null,
    name            varchar(50),
    status          char(2)     not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index contract__tenant on contract (tenant_id);
create index contract__client on contract (client);
create index contract__curr_mng on contract (curr_mng);

drop table if exists project;
create table project
(
    id              int         auto_increment primary key,
    tenant_id          int         not null,       -- virtual fk to tenant.id
    contract        int         not null,       -- virtual fk to client.id
    curr_mng        int         not null,       -- virtual fk to emp.id
    num             varchar(50) not null,
    name            varchar(50),
    status          char(5)     not null,
    created_at      datetime    not null,
    created_by      int         not null,
    last_updated_at datetime,
    last_updated_by int
);

create index project__tenant on project (tenant_id);
create index project__contract on project (contract);
create index project__curr_mng on project (curr_mng);

drop table if exists client_mng;
create table client_mng
(
    id              int      auto_increment primary key,
    tenant_id          int      not null,       -- virtual fk to tenant.id
    client          int      not null,       -- virtual fk to client.id
    mng             int      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index client_mng__tenant on client_mng (tenant_id);
create index client_mng__contract on client_mng (client);
create index client_mng__mng on client_mng (mng);


drop table if exists contract_mng;
create table contract_mng
(
    id              int      auto_increment primary key,
    tenant_id          int      not null,       -- virtual fk to tenant.id
    contract        int      not null,       -- virtual fk to contract.id
    mng             int      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index contract_mng__tenant on contract_mng (tenant_id);
create index contract_mng__contract on contract_mng (contract);
create index contract_mng__mng on contract_mng (mng);


drop table if exists project_mng;
create table project_mng
(
    id              int      auto_increment primary key,
    tenant_id          int      not null,       -- virtual fk to tenant.id
    project         int      not null,       -- virtual fk to project.id
    mng             int      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index project_mng__tenant on project_mng (tenant_id);
create index project_mng__project on project_mng (project);
create index project_mng__mng on project_mng (mng);

drop table if exists project_member;
create table project_member
(
    id              int      auto_increment primary key,
    tenant_id          int      not null,       -- virtual fk to tenant.id
    project         int      not null,      -- virtual fk to project.id
    emp             int      not null,       -- virtual fk to emp.id
    estimate_invest_ratio    smallint not null,
    start_at        datetime not null,
    end_at          datetime,
    status          char(2)  not null,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index project_member__tenant on project_member (tenant_id);
create index project_member__project on project_member (project);
create index project_member__emp on project_member (emp);

-- Projectmng module end

-- Effortmng module begin

drop table if exists effort_record;
create table effort_record
(
    id              int      auto_increment primary key,
    tenant_id          int      not null,       -- virtual fk to tenant.id
    project         int      not null,      -- virtual fk to project.id
    emp             int      not null,       -- virtual fk to emp.id
    work_date       date     not null,
    effort          decimal(2,1) not null,
    notes           varchar(255),
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index effort_record__tenant on effort_record (tenant_id);
create index effort_record__project on effort_record (project);
create index effort_record__emp on effort_record (emp);

-- Effortmng module end




