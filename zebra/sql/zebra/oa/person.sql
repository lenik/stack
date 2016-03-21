--\import lily.account.user
--\import lily.account.group
--\import zebra.oa.contact
--\import zebra.oa.org
--\import zebra.oa.orgunit

    create sequence person_seq start with 1000;
    create table person(
        id          int primary key default nextval('person_seq'),
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    varchar(40),
        peer        boolean not null default false,
        customer    boolean not null default false,
        supplier    boolean not null default false,
        subject     varchar(200),

        contact     int
            references contact(_id) on update cascade on delete set null,

        bank        varchar(50),
        bankacc     varchar(30),

        employee    boolean not null default false,
        gender      char,
        homeland    varchar(10),
        passport    varchar(20),

        ssn         varchar(20) unique,     -- social security number
        dln         varchar(20)             -- driver's license number
    );

    create index person_label       on person(label);
    create index person_lastmod     on person(lastmod desc);
    create index person_priority    on person(priority);
    create index person_state       on person(state);
    create index person_uid_acl     on person(uid, acl);

    alter table contact add constraint contact_fk_person foreign key(person)
            references person(id)   on update cascade on delete cascade deferrable;
