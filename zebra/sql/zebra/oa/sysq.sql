    create sequence sysq_seq start with 1000;
    create table sysq(
        id          int primary key default nextval('sysq_seq'),
--\mixin lily.mixin.Label

        class       varchar(80) not null,
        summary     varchar(30) not null,
        quota       double precision not null,
        userquota   double precision null,

        constraint sysq_uk unique(class, summary)
    );

    create index sysq_label on sysq(label);
