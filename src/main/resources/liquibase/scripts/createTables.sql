-- liquebase formatted sql
-- changeset abdullinru:1
create table owners
(
    id         BigSerial      primary key,
    name       varchar        not null unique,
    pin   varchar        not null
);
create table accounts
(
    id         BigSerial    primary key,
    number     varchar      not null,
    balance    numeric      not null,
    owner_id   BigSerial    REFERENCES owners (id)
);
create table histories
(
    id                BigSerial    primary key,
    date_time         DATE         not null,
    sender_id         BigSerial,
    receiver_id       BigSerial,
    change_balance    numeric      not null,
    type              varchar      not null
);