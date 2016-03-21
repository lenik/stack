--\import zebra.oa.org

    create sequence orgunit_seq start with 1000;

    create table orgunit(
        id          int primary key default nextval('orgunit_seq'),
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        org         int not null
            references org(id) on update cascade on delete cascade,

        parent      int
            references orgunit(id) on update cascade on delete cascade,

        depth       int not null default -1,

        contact     int
            references contact(_id) on update cascade on delete set null
    );

    create index orgunit_label      on orgunit(label);
    create index orgunit_lastmod    on orgunit(lastmod desc);
    create index orgunit_priority   on orgunit(priority);
    create index orgunit_state      on orgunit(state);
    create index orgunit_uid_acl    on orgunit(uid, acl);

    alter table contact add constraint contact_fk_ou foreign key(ou)
            references orgunit(id)  on update cascade on delete cascade deferrable;
