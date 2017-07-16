--\import zebra.oa.org
--\import zebra.oa.person
--\import zebra.oa.topic

    create sequence acdoc_seq start with 1000;
    create table acdoc(
        id          bigint primary key default nextval('acdoc_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Ver
--\mixin lily.mixin.Mi
--\mixin lily.mixin.Msg

        cat         int
            references _cat(id) on update cascade,
        phase       int
            references _phase(id) on update cascade,

        prev        int             -- previous doc
            references acdoc(id) on update cascade on delete set null,

        topic       int
            references topic(id) on update cascade on delete set null,

        org         int
            references org(id) on update cascade,

        person      int
            references person(id) on update cascade,

        val         double precision not null,

        ndebit      double precision not null default 0,
        ncredit     double precision not null default 0
    );

    create index acdoc_lastmod         on acdoc(lastmod desc);
    create index acdoc_priority        on acdoc(priority);
    create index acdoc_state           on acdoc(state);
    create index acdoc_subject         on acdoc(subject);
    create index acdoc_uid_acl         on acdoc(uid, acl);
    create index acdoc_year            on acdoc(year);
    create index acdoc_t0t1            on acdoc(t0, t1);
    create index acdoc_t1              on acdoc(t1);
