--\import lily.type.CoMessage
--\import zebra.oa.org
--\import zebra.oa.person
--\import zebra.oa.topic

    create sequence sdoc_seq start with 1000;
    create table sdoc(                  -- sales/subscription doc
        id          int primary key default nextval('sdoc_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
        -- contract date .. deadline
--\mixin lily.mixin.Mi
        -- subscription category/phase
--\mixin lily.mixin.Ver
--\mixin lily.mixin.Msg

        prev        int             -- previous doc
            references sdoc(id) on update cascade on delete set null,

        topic       int
            references topic(id) on update cascade on delete set null,

        org         int
            references org(id) on update cascade,

        person      int
            references person(id) on update cascade,

        size        int not null default 0,
        qty         numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0
    );

    create index sdoc_lastmod          on sdoc(lastmod desc);
    create index sdoc_priority         on sdoc(priority);
    create index sdoc_state            on sdoc(state);
    create index sdoc_subject          on sdoc(subject);
    create index sdoc_uid_acl          on sdoc(uid, acl);
    create index sdoc_t0t1             on sdoc(t0, t1);
    create index sdoc_t1               on sdoc(t1);
