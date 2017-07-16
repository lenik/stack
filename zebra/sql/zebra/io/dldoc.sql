--\import zebra.oa.contact
--\import zebra.oa.org
--\import zebra.oa.person

    create sequence dldoc_seq start with 1000;
    create table dldoc(             -- sales/subscription doc
        id          int primary key default nextval('dldoc_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex

        -- t0: package date
        -- t1: recv date
--\mixin lily.mixin.Mi
--\mixin lily.mixin.Ver

--\mixin lily.mixin.Msg

        cat         int
            references _cat(id) on update cascade,
        phase       int
            references _phase(id) on update cascade,

        prev        int             -- previous doc
            references dldoc(id) on update cascade on delete set null,

        sdoc        int,

        org         int
            references org(id) on update cascade,

        person      int
            references person(id) on update cascade,

        shipdest    int
            references contact(_id) on update cascade on delete set null,

        shipper     int
            references org(id) on update cascade on delete set null,

        shipment    varchar(30),
        shiplog     varchar(200),
        shipcost    numeric(20, 2) not null default 0,

        size        int not null default 0,
        qty         numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0
    );

    create index dldoc_lastmod          on dldoc(lastmod desc);
    create index dldoc_priority         on dldoc(priority);
    create index dldoc_state            on dldoc(state);
    create index dldoc_subject          on dldoc(subject);
    create index dldoc_uid_acl          on dldoc(uid, acl);
    create index dldoc_t0t1             on dldoc(t0, t1);
    create index dldoc_t1               on dldoc(t1);
