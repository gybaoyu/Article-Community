create table article
(
    id      int auto_increment
        primary key,
    uid     int                  not null,
    view    int        default 0 not null,
    `like`  int        default 0 not null,
    author  varchar(255)         not null,
    title   varchar(255)         not null,
    content longtext             not null,
    des     varchar(255)         null,
    tag     varchar(255)         null,
    img     varchar(255)         null,
    time    timestamp            not null,
    pass    tinyint(1) default 0 not null,
    top     tinyint(1) default 0 not null
);
create table chat
(
    id   int auto_increment
        primary key,
    name varchar(255) not null,
    `to` varchar(255) null,
    text text         not null,
    time timestamp    not null
);
create table comment
(
    id   int auto_increment
        primary key,
    aid  int          not null,
    time timestamp    not null,
    text varchar(255) not null,
    uid  int          not null
);
create table file
(
    id       int auto_increment
        primary key,
    uid      int          null,
    filename varchar(255) not null,
    username varchar(255) null,
    time     timestamp    null
);
create table reply
(
    uid    int          not null,
    `from` int          not null,
    `to`   int          not null,
    text   varchar(255) not null,
    time   timestamp    not null,
    aid    int          not null
);
create table user
(
    id       int auto_increment
        primary key,
    name     varchar(255) not null,
    password varchar(255) not null,
    type     int          not null,
    email    varchar(255) null,
    clazz    int          null,
    sess     int          null
);
