--\import lily.type.CoMessage
--\import zebra.oa.org
--\import zebra.oa.person
--\import zebra.oa.topic

    create sequence stdoc_seq start with 1000;
    create table stdoc(
        id          int primary key default nextval('stdoc_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi
        -- cat. stock order subject
        -- phase. stock stage
--\mixin lily.mixin.Msg
--\mixin lily.mixin.Ver

        prev        int             -- previous doc
            references stdoc(id) on update cascade on delete set null,

        topic       int
            references topic(id) on update cascade on delete set null,

        org         int
            references org(id) on update cascade,

        ou          int,

        person      int
            references person(id) on update cascade,

        size        int not null default 0,
        qty         numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0
    );

    create index stdoc_lastmod         on stdoc(lastmod desc);
    create index stdoc_priority        on stdoc(priority);
    create index stdoc_state           on stdoc(state);
    create index stdoc_subject         on stdoc(subject);
    create index stdoc_uid_acl         on stdoc(uid, acl);
    create index stdoc_t0t1            on stdoc(t0, t1);
    create index stdoc_t1              on stdoc(t1);
