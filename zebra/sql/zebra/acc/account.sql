-- drop table if exists "account";

    create table "account"(
        id          int primary key,
        code        varchar(20) unique,
--\mixin lily.mixin.LabelExVer
        sign        int not null default 0
    );

