--\import lily.type.CoMessage
--\import lily.base.tag
--\import zebra.oa.org
--\import zebra.oa.person

    create sequence topic_seq start with 1000;
    create table topic(
        id          int primary key default nextval('topic_seq'),

--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi
--\mixin lily.mixin.Msg
--\mixin lily.mixin.Ver

        nread       int not null default 0,
        nvote       int not null default 0,
        nlike       int not null default 0,

        val         double precision not null default 0 -- estimated
        -- valmax   double precision,
    );

    create index topic_lastmod        on topic(lastmod desc);
    create index topic_priority_nvote on topic(priority, nvote desc);
    create index topic_state          on topic(state);
    create index topic_subject        on topic(subject);
    create index topic_t0t1           on topic(t0, t1);
    create index topic_t1             on topic(t1);
    create index topic_uid_acl        on topic(uid, acl);

    insert into topic(id, subject, text, cat)
        values(1, '未分类（机会）', '未指定机会的行动集', 401);

    create sequence topicparty_seq;
    create table topicparty(
        id          bigint primary key default nextval('topicparty_seq'),

        topic       int not null
            references topic(id) on update cascade on delete set null,

        person      int
            references person(id) on update cascade on delete set null,

        org         int
            references org(id) on update cascade on delete set null,

        description varchar(60)
    );

    create sequence topicatt_seq;
    create table topicatt(
        id          int primary key default nextval('topicatt_seq'),

        topic       int not null
            references topic(id) on update cascade on delete cascade,

        att         int not null
            references att(id) on update cascade on delete cascade,

        val         varchar(200),

        constraint topicatt_uk unique(topic, att)
    );

    create sequence topictag_seq;
    create table topictag(
        id          int primary key default nextval('topictag_seq'),
        topic       int not null
            references topic(id) on update cascade on delete cascade,

        tag         int not null
            references tag(id) on update cascade on delete cascade
    );

    create sequence topicvote_seq;
    create table topicvote(
        id          int primary key default nextval('topicvote_seq'),
        topic       int not null
            references topic(id) on update cascade on delete cascade,

        op          int not null
            references "user"(id) on update cascade on delete cascade,

        n           int not null default 1,
        time        timestamp not null default now()
    );
