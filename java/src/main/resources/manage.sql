# 数据库管理

# 数据库
create table t_database(
    id char(32) primary key,
    nickname varchar(255) default '' not null,
    name varchar(255) default '' not null,
    host char(15) default '127.0.0.1' not null,
    port int(4) default 0 not null,
    parameter varchar(255) default '' not null,
    username varchar(255) default '' not null,
    password varchar(255) default '' not null,
    type int default 0 not null,
    create_time datetime default '1998-06-08 00:00:00' not null,
    update_time datetime default '1998-06-08 00:00:00' not null,
    is_delete int default 0 not null
);
