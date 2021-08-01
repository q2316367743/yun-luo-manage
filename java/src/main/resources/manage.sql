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
    type        tinyint      default 0 not null,
    type_name   varchar(16)  default 0 not null,
    version     varchar(16)  default '' not null,
    create_time datetime     default '1998-08-06 00:00:00' not null,
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

create table t_server_config
(
    id        char(32) primary key,
    path      varchar(255) default '' not null,
    server_id char(32)     default '' not null,
    constraint fk_command_server foreign key (server_id) references t_server (id)
)
