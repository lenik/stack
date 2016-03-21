--\import lily.type.CoMessage
--\import zebra.oa.org
--\import zebra.oa.person
--\import zebra.oa.topic

    create sequence reply_seq;
    create table reply(
        id          int primary key default nextval('reply_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi
--\mixin lily.mixin.Msg
--\mixin lily.mixin.Ver

        nvote       int not null default 0,

        topic       int not null
            references topic(id) on update cascade on delete set null,

        parent      int
            references reply(id) on update cascade on delete set null,

        changes     text[]
    );

    create index reply_lastmod      on reply(lastmod desc);
    create index reply_priority     on reply(priority);
    create index reply_state        on reply(state);
    create index reply_t0t1         on reply(t0, t1);
    create index reply_t1           on reply(t1);
    create index reply_uid_acl      on reply(uid, acl);

-- drop table if exists replyparty;
    create sequence replyparty_seq;
    create table replyparty(
        id          int primary key default nextval('replyparty_seq'),
        reply       int not null
            references reply(id) on update cascade on delete cascade,

        person      int
            references person(id) on update cascade on delete cascade,

        org         int
            references org(id) on update cascade on delete cascade,

        description varchar(60)
    );

-- drop table if exists replyatt;
    create sequence replyatt_seq;
    create table replyatt(
        id          int primary key default nextval('replyatt_seq'),

        reply       int not null
            references reply(id) on update cascade on delete cascade,

        att         int not null
            references att(id) on update cascade on delete cascade,

        val         varchar(200),

        constraint replyatt_uk unique(reply, att)
    );

-- drop table if exists replytag;
    create sequence replytag_seq;
    create table replytag(
        id          int primary key default nextval('replytag_seq'),
        reply       int not null
            references reply(id) on update cascade on delete cascade,

        tag         int not null
            references tag(id) on update cascade on delete cascade
    );

-- drop table if exists replyvote;
    create sequence replyvote_seq;
    create table replyvote(
        id          int primary key default nextval('replyvote_seq'),
        reply       int not null
            references reply(id) on update cascade on delete cascade,

        op          int not null
            references "user"(id) on update cascade on delete cascade,

        n           int not null default 1,
        time        timestamp not null default now()
    );
