--\import lily.type.CoMessage

    create sequence issue_seq start with 1000;

    create table issue(
        id          int primary key default nextval('issue_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi
        -- cat. stock order subject
        -- phase. stock stage
--\mixin lily.mixin.Msg
--\mixin lily.mixin.Ver

        prev        int             -- previous doc
            references issue(id) on update cascade on delete set null,

        topic       int
            references topic(id) on update cascade on delete set null,

        sdoc        int,
        art         int,
        sn          varchar(80),

        qty         numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0
    );

    create index issue_lastmod          on issue(lastmod desc);
    create index issue_priority         on issue(priority);
    create index issue_state            on issue(state);
    create index issue_subject          on issue(subject);
    create index issue_uid_acl          on issue(uid, acl);
    create index issue_t0t1             on issue(t0, t1);
    create index issue_t1               on issue(t1);
