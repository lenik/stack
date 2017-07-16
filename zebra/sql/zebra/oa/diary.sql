--\import zebra.oa.org
--\import zebra.oa.person

    create sequence diary_seq start with 1000;
    create table diary(
        id          int primary key default nextval('diary_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi
--\mixin lily.mixin.Msg
--\mixin lily.mixin.Ver

	cat int
	    references _cat(id) on update cascade,
	phase int
	    references _phase(id) on update cascade
    );

    create index diary_lastmod        on diary(lastmod desc);
    create index diary_priority       on diary(priority);
    create index diary_state          on diary(state);
    create index diary_subject        on diary(subject);
    create index diary_t0t1           on diary(t0, t1);
    create index diary_t1             on diary(t1);
    create index diary_uid_acl        on diary(uid, acl);

    create sequence diaryparty_seq;
    create table diaryparty(
        id          bigint primary key default nextval('diaryparty_seq'),

        diary       int not null
            references diary(id) on update cascade on delete set null,

        person      int
            references person(id) on update cascade on delete set null,

        org         int
            references org(id)  on update cascade on delete set null,

        description varchar(60)
    );
