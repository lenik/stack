--\import lily.account.user

    create sequence userq_seq start with 1000;
    create table userq(
        id          int primary key default nextval('userq_seq'),
--\mixin lily.mixin.Label

        uid         int not null
            references "user"(id) on update cascade on delete cascade,

        class       varchar(80) not null,
        summary     varchar(30) not null,
        quota       double precision not null,

        constraint userq_uk unique(uid, class, summary)
    );

    create index userq_label        on userq(label);
