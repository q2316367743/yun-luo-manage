/* 数据库管理 */

/* 数据库 */
create table t_database
(
    id          char(32) primary key,
    nickname    varchar(255) default '' not null,
    name        varchar(255) default '' not null,
    host        char(15)     default '127.0.0.1' not null,
    port        int(4)       default 0 not null,
    parameter   varchar(255) default '' not null,
    username    varchar(255) default '' not null,
    password    varchar(255) default '' not null,
    type        int          default 0 not null,
    create_time datetime     default '1998-08-06 00:00:00' not null,
    update_time datetime     default '1998-08-06 00:00:00' not null,
    is_delete   int          default 0 not null
);

/* 服务器管理 */
create table t_server
(
    id          char(32) primary key,
    name        varchar(255) default '' not null,
    type        varchar(16)  default 0 not null,
    version     varchar(16)  default '' not null,
    create_id   char(32)     default '' not null,
    create_time datetime     default '1998-08-06 00:00:00' not null,
    update_id   char(32)     default '' not null,
    update_time datetime     default '1998-08-06 00:00:00' not null,
    is_delete   int          default 0 not null
);

/* 服务器软件可执行命令 */
create table t_server_command
(
    id        char(32) primary key,
    name      varchar(32)  default '' not null,
    command   varchar(255) default '' not null,
    server_id char(32)     default '' not null,
    constraint fk_command_server foreign key (server_id) references t_server (id)
);

/* 服务器软件配置文件 */
create table t_server_config
(
    id        char(32) primary key,
    path      varchar(255) default '' not null,
    server_id char(32)     default '' not null,
    constraint fk_command_server foreign key (server_id) references t_server (id)
);

/* 权限分类表 */
create table t_permission_category
(
    id    char(32) primary key,
    name  varchar(32)        default '' not null,
    value varchar(16) unique default '' not null
);

/* 权限表，初始化后就不会改变 */
create table t_permission
(
    id          char(32) primary key,
    name        varchar(32)        default '' not null,
    value       varchar(16) unique default '' not null,
    category_id char(32)           default '' not null
);

/* 角色表 */
create table t_role
(
    id          char(32) primary key,
    name        varchar(32)        default '' not null,
    value       varchar(16) unique default '' not null,
    create_id   char(32)           default '' not null,
    create_time datetime           default '1998-08-06 00:00:00' not null,
    update_id   char(32)           default '' not null,
    update_time datetime           default '1998-08-06 00:00:00' not null,
    is_delete   int                default 0 not null
);

/* 角色 - 权限表 */
create table t_role_permission
(
    id            char(32) primary key,
    role_id       char(32) default '' not null,
    permission_id char(32) default '' not null
);

/* 用户表 */
create table t_user
(
    id          char(32) primary key,
    username    varchar(32) default '' not null,
    password    varchar(32) default '' not null,
    nickname    varchar(32) default '' not null,
    role_id     varchar(32) default '' not null,
    create_id   char(32)    default '' not null,
    create_time datetime    default '1998-08-06 00:00:00' not null,
    update_id   char(32)    default '' not null,
    update_time datetime    default '1998-08-06 00:00:00' not null,
    is_delete   int         default 0 not null,
    is_enable   int         default 1 not null
);

create index idx_user_username_password on t_user (username, password);

/* 用户 - 权限视图 */
create view v_user_permission as
select u.id as `user_id`, tc.value as `category_value`, p.value as `permission_value`
from t_permission p
         left join t_role_permission rp
                   on rp.permission_id = p.id
         left join t_role r
                   on rp.role_id = r.id
         left join t_user u
                   on u.role_id = rp.role_id
         left join t_permission_category tc
                   on p.category_id = tc.id
where r.is_delete = 0;

/* 初始化权限，用户，密码 */
insert into t_permission_category (id, name, value)
values ('1', '文件管理', 'file');
insert into t_permission_category (id, name, value)
values ('2', '服务器管理', 'server');
insert into t_permission_category (id, name, value)
values ('3', '系统管理', 'system');

insert into t_permission
values ('1', '文件管理', 'file', '1');
insert into t_permission
values ('2', '服务器管理', 'server', '2');
insert into t_permission
values ('3', '角色管理', 'role', '3');
insert into t_permission
values ('4', '用户管理', 'user', '3');

insert into t_role
values ('1', '超级管理员', 'admin', '0', '1998-08-06 00:00:00', '0', '1998-08-06 00:00:00', 0);

insert into t_role_permission
values ('1', '1', '1');
insert into t_role_permission
values ('2', '1', '2');
insert into t_role_permission
values ('3', '1', '3');
insert into t_role_permission
values ('4', '1', '4');

insert into t_user
values ('1', 'esion', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '1', '0', '1998-08-06 00:00:00', '0',
        '1998-08-06 00:00:00', 0, 1);