/* 数据库管理 */

/* 数据库 */
create table t_database
(
    id          char(32) primary key,
    nickname    varchar(255) default '' not null,
    name        varchar(255) default '' not null,
    host        char(15)     default '127.0.0.1' not null,
    port        int(5)       default 3306 not null,
    parameter   varchar(255) default '' not null,
    username    varchar(255) default '' not null,
    password    varchar(255) default '' not null,
    type        int          default 0 not null,
    create_id   char(32)     default '' not null,
    create_time datetime     default '1998-08-06 00:00:00' not null,
    update_id   char(32)     default '' not null,
    update_time datetime     default '1998-08-06 00:00:00' not null,
    is_delete   int          default 0 not null
);

/* 服务器管理 */
create table t_server
(
    id               char(32) primary key,
    name             varchar(255) default '' not null,
    type             varchar(16)  default 0 not null,
    version          varchar(16)  default '' not null,
    application_name varchar(255) default '' not null,
    create_id        char(32)     default '' not null,
    create_time      datetime     default '1998-08-06 00:00:00' not null,
    update_id        char(32)     default '' not null,
    update_time      datetime     default '1998-08-06 00:00:00' not null,
    is_delete        int          default 0 not null
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

/* 权限表，初始化后就不会改变 */
create table t_permission
(
    id    char(32) primary key,
    name  varchar(32)        default '' not null,
    value varchar(16) unique default '' not null
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
select u.id as `user_id`, p.value as `permission_value`
from t_permission p
         left join t_role_permission rp
                   on rp.permission_id = p.id
         left join t_role r
                   on rp.role_id = r.id
         left join t_user u
                   on u.role_id = rp.role_id
where r.is_delete = 0;

/* 初始化权限，用户，密码 */

insert into t_permission
values ('10101', '文件管理 & 全部', 'file&a');
insert into t_permission
values ('20101', '服务器管理 & 列表', 'server&l');
insert into t_permission
values ('20102', '服务器管理 & 新增', 'server&a');
insert into t_permission
values ('20103', '服务器管理 & 修改', 'server&u');
insert into t_permission
values ('20104', '服务器管理 & 删除', 'server&d');
insert into t_permission
values ('20105', '服务器管理 & 详情', 'server&i');
insert into t_permission
values ('30101', '系统管理 - 角色管理 & 列表', 'system-role&l');
insert into t_permission
values ('30102', '系统管理 - 角色管理 & 新增', 'system-role&a');
insert into t_permission
values ('30103', '系统管理 - 角色管理 & 修改', 'system-role&u');
insert into t_permission
values ('30104', '系统管理 - 角色管理 & 删除', 'system-role&d');
insert into t_permission
values ('30201', '系统管理 - 用户管理 & 列表', 'system-user&l');
insert into t_permission
values ('30202', '系统管理 - 用户管理 & 新增', 'system-user&a');
insert into t_permission
values ('30203', '系统管理 - 用户管理 & 修改', 'system-user&u');
insert into t_permission
values ('30204', '系统管理 - 用户管理 & 删除', 'system-user&d');

insert into t_role
values ('1', '超级管理员', 'admin', '0', '1998-08-06 00:00:00', '0', '1998-08-06 00:00:00', 0);

insert into t_role_permission
values ('1', '1', '10101');
insert into t_role_permission
values ('2', '1', '20101');
insert into t_role_permission
values ('3', '1', '20102');
insert into t_role_permission
values ('4', '1', '20103');
insert into t_role_permission
values ('5', '1', '20104');
insert into t_role_permission
values ('6', '1', '20105');
insert into t_role_permission
values ('7', '1', '30101');
insert into t_role_permission
values ('8', '1', '30102');
insert into t_role_permission
values ('9', '1', '30103');
insert into t_role_permission
values ('10', '1', '30104');
insert into t_role_permission
values ('11', '1', '30201');
insert into t_role_permission
values ('12', '1', '30202');
insert into t_role_permission
values ('13', '1', '30203');
insert into t_role_permission
values ('14', '1', '30204');

insert into t_user
values ('1', 'esion', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '1', '0', '1998-08-06 00:00:00', '0',
        '1998-08-06 00:00:00', 0, 1);