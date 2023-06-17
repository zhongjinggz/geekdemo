-- Tenant module begin
drop table if exists tenant;
create table tenant
(
    id              bigint auto_increment primary key,
    name            varchar(50) not null,
    status_code     char(2)     not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime    null,
    last_updated_by bigint         null
);
-- Tenant module end

-- Orgmnt module begin
drop table if exists post;
create table post
(
    code            char(10)    primary key,
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    name            varchar(50) not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index post__tenant on post (tenant_id);

drop table if exists org_type;
create table org_type
(
    code            char(10)             not null primary key,
    tenant_id       bigint                  not null,
    name            varchar(50)          not null,
    status_code     char(2) default 'EF' not null,
    created_at      datetime             not null,
    created_by      bigint                  not null,
    last_updated_at datetime             null,
    last_updated_by bigint                  null
);

create index org_type__tenant on org_type (tenant_id);

drop table if exists org;
create table org
(
    id              bigint         auto_increment primary key,
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    superior_id     bigint,                        -- virtual fk to org.id
    org_type_code   char(10)    not null,       -- virtual fk to org_type.code
    leader_id       bigint,                        -- virtual fk to emp.id
    name            varchar(50) not null,
    status_code     char(10)    not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index org__tenant on org (tenant_id);
create index org__superior on org (superior_id);
create index org__org_type on org (org_type_code);
create index org__leader on org (leader_id);

drop table if exists emp;
create table emp
(
    id              bigint auto_increment primary key,
    tenant_id       bigint                   not null,
    org_id          bigint                   null,
    emp_num         varchar(20)           not null,
    id_num          varchar(20)           not null,
    name            varchar(20)           not null,
    gender_code     varchar(20)           null,
    dob             date                  null,
    status_code     char(3) default 'REG' not null,
    version         bigint default 0      null,
    created_at      datetime              not null,
    created_by      bigint                   not null,
    last_updated_at datetime              null,
    last_updated_by bigint                   null
);

create index emp__org on emp (org_id);
create index emp__tenant on emp (tenant_id);

drop table if exists skill;
create table skill_type
(
    id              bigint auto_increment primary key,
    tenant_id       bigint                   not null,
    name            varchar(50)              not null,
    created_at      datetime                 not null,
    created_by      bigint                   not null,
    last_updated_at datetime                 null,
    last_updated_by bigint                   null
);

drop table if exists skill;
create table skill
(
    id              bigint auto_increment primary key,
    tenant_id       bigint                   not null,
    skill_type_id   bigint                   not null,
    emp_id          bigint                   not null,
    level_code      char(3)                  null,
    duration        int                      null,
    created_at      datetime                 not null,
    created_by      bigint                   not null,
    last_updated_at datetime                 null,
    last_updated_by bigint                   null
);
create index skill__emp_id on skill (emp_id);

drop table if exists work_experience;
create table work_experience
(
    id              bigint auto_increment primary key,
    tenant_id       bigint                   not null,
    emp_id          bigint                   not null,
    company         varchar(50)              not null,
    start_date      date                     not null,
    end_date        date                     null,
    created_at      datetime                 not null,
    created_by      bigint                   not null,
    last_updated_at datetime                 null,
    last_updated_by bigint                   null
);
create index work_experience__emp_id on work_experience (emp_id);


drop table if exists emp_num_counter;
create table emp_num_counter
(
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    year_num        int            not null,
    max_emp_num     int            not null,
    primary key (tenant_id, year_num)
);


drop table if exists emp_post;
create table emp_post
(
    emp_id          bigint,                        -- pk, virtual fk to tenant.id
    post_code       char(10),                   -- pk, virtual fk to org.id
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint,
    primary key (emp_id, post_code)
);

create index emp_post__post on emp_post (post_code);
-- Orgmnt module end

-- Projectmng module begin
drop table if exists client;
create table client
(
    id              bigint         auto_increment primary key,
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    curr_mng_id     bigint         not null,       -- virtual fk to emp.id
    name            varchar(10) not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index client__tenant on client (tenant_id);
create index client__curr_mng on client (curr_mng_id);

drop table if exists contract;
create table contract
(
    id              bigint         auto_increment primary key,
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    client_id       bigint         not null,       -- virtual fk to client.id
    curr_mng_id     bigint         not null,       -- virtual fk to emp.id
    num             varchar(50) not null,
    name            varchar(50),
    status_code     char(2)     not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index contract__tenant on contract (tenant_id);
create index contract__client on contract (client_id);
create index contract__curr_mng on contract (curr_mng_id);

drop table if exists project;
create table project
(
    id              bigint         auto_increment primary key,
    tenant_id       bigint         not null,       -- virtual fk to tenant.id
    contract_id     bigint         not null,       -- virtual fk to client.id
    curr_mng_id     bigint         not null,       -- virtual fk to emp.id
    num             varchar(50) not null,
    name            varchar(50),
    status_code     char(5)     not null,
    created_at      datetime    not null,
    created_by      bigint         not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index project__tenant on project (tenant_id);
create index project__contract on project (contract_id);
create index project__curr_mng on project (curr_mng_id);

drop table if exists client_mng;
create table client_mng
(
    id              bigint      auto_increment primary key,
    tenant_id       bigint      not null,       -- virtual fk to tenant.id
    client_id       bigint      not null,       -- virtual fk to client.id
    mng_id          bigint      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index client_mng__tenant on client_mng (tenant_id);
create index client_mng__contract on client_mng (client_id);
create index client_mng__mng on client_mng (mng_id);


drop table if exists contract_mng;
create table contract_mng
(
    id              bigint      auto_increment primary key,
    tenant_id       bigint      not null,       -- virtual fk to tenant.id
    contract_id     bigint      not null,       -- virtual fk to contract.id
    mng_id          bigint      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index contract_mng__tenant on contract_mng (tenant_id);
create index contract_mng__contract on contract_mng (contract_id);
create index contract_mng__mng on contract_mng (mng_id);


drop table if exists project_mng;
create table project_mng
(
    id              bigint      auto_increment primary key,
    tenant_id       bigint      not null,       -- virtual fk to tenant.id
    project_id      bigint      not null,       -- virtual fk to project.id
    mng_id          bigint      not null,       -- virtual fk to emp.id
    start_at        datetime not null,
    end_at          datetime,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index project_mng__tenant on project_mng (tenant_id);
create index project_mng__project on project_mng (project_id);
create index project_mng__mng on project_mng (mng_id);

drop table if exists project_member;
create table project_member
(
    id              bigint      auto_increment primary key,
    tenant_id       bigint      not null,       -- virtual fk to tenant.id
    project_id      bigint      not null,      -- virtual fk to project.id
    emp_id          bigint      not null,       -- virtual fk to emp.id
    estimate_invest_ratio    smallint not null,
    start_at        datetime not null,
    end_at          datetime,
    status_code     char(2)  not null,
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index project_member__tenant on project_member (tenant_id);
create index project_member__project on project_member (project_id);
create index project_member__emp on project_member (emp_id);

-- Projectmng module end

-- Effortmng module begin

drop table if exists effort_record;
create table effort_record
(
    id              bigint      auto_increment primary key,
    tenant_id       bigint      not null,       -- virtual fk to tenant.id
    project_id      bigint      not null,      -- virtual fk to project.id
    emp_id          bigint      not null,       -- virtual fk to emp.id
    work_date       date     not null,
    effort          decimal(2,1) not null,
    notes           varchar(255),
    created_at      datetime not null,
    created_by      bigint   not null,
    last_updated_at datetime,
    last_updated_by bigint
);

create index effort_record__tenant on effort_record (tenant_id);
create index effort_record__project on effort_record (project_id);
create index effort_record__emp on effort_record (emp_id);

-- Effortmng module end




