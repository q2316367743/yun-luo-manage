# 数据库管理

# 数据库
create table t_database(
    id varchar(32) primary key,
    nickname varchar(255) default '' not null,
    name varchar(255) default '' not null,
    host varchar(15) default '0.0.0.0' not null,
    port int(4) default 0 not null,
    parameter varchar(255) default '' not null,
    username varchar(255) default '' not null,
    password varchar(255) default '' not null,
    status int default 0 not null,
    type int default 0 not null,
    create_time datetime default '1998-06-08 00:00:00' not null,
    update_time datetime default '1998-06-08 00:00:00' not null,
    is_delete int default 0 not null
);

# 表
create table t_table(
    id varchar(32) primary key,
    name varchar(255) default '' not null,
    database_id varchar(32) default 0 not null,
    create_time datetime default '1998-06-08 00:00:00' not null,
    is_delete int default 0 not null,
    constraint fk_database_table foreign key(database_id) references t_database(id)
);

# 字段
create table t_field(
    id varchar(32) primary key,
    name varchar(255) default '' not null,
    type varchar(16) default '' not null,
    is_null varchar(16) default '' not null,
    key_type varchar(16) default '' not null,
    default_value varchar(255) default '' not null,
    extra varchar(16) default '' not null,
    database_id varchar(32) default 0 not null,
    table_id varchar(32) default 0 not null,
    create_time datetime default '1998-06-08 00:00:00' not null,
    is_delete int default 0 not null,
    constraint fk_database_field foreign key(database_id) references t_database(id),
    constraint fk_table_field foreign key(database_id) references t_database(id)
);