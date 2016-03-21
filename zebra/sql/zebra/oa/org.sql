--\import lily.account.group
--\import lily.account.user
--\import zebra.oa.contact

    create sequence org_seq start with 1000;
    create table org(
        id          int primary key default nextval('org_seq'),
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    varchar(40),
        peer        boolean not null default false,
        supplier    boolean not null default false,
        customer    boolean not null default false,
        subject     varchar(200),

        contact     int
            references contact(_id) on update cascade on delete set null,

        bank        varchar(50),
        bankacc     varchar(30),

        size        int not null default 0,
        taxid       varchar(20) unique,

        shipper     boolean not null default false
    );

    create index org_label          on org(label);
    create index org_lastmod        on org(lastmod desc);
    create index org_priority       on org(priority);
    create index org_state          on org(state);
    create index org_uid_acl        on org(uid, acl);

    alter table contact add constraint fk_org foreign key(org)
        references org(id) on update cascade on delete cascade deferrable;
