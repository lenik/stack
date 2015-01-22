-- issue, comment

-- drop table if exists issue;
    create sequence issue_seq start with 1000;
    create table issue(
        id          int primary key default nextval('issue_seq'),
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        uid         int,
        gid         int,
        mode        int not null default b'110100000'::int,
        acl         int,
        
        t0          date not null,  -- contract date
        t1          date,           -- contract deadline
        year        int not null default 0, -- same year of t0.
        
        op          int,
        subject     varchar(200) not null,
        text        text,
        form        int,
        args        text,           -- used with the form.
        cat         int,            -- subscription category
        phase       int,            -- subscription phase
        
        prev        int,            -- previous doc
        topic       int,
        sdoc        int,
        art         int,
        sn          varchar
        
        qty         numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0,
        
        constraint issue_fk_cat      foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint issue_fk_form     foreign key(form)
            references form(id)         on update cascade on delete set null,
        constraint issue_fk_gid      foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint issue_fk_op       foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint issue_fk_org      foreign key(org)
            references org(id)          on update cascade,
        constraint issue_fk_person   foreign key(person)
            references person(id)       on update cascade,
        constraint issue_fk_phase    foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint issue_fk_prev     foreign key(prev)
            references issue(id)        on update cascade on delete set null,
        constraint issue_fk_topic    foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint issue_fk_uid      foreign key(uid)
            references "user"(id)       on update cascade on delete set null
    );

    create index issue_lastmod          on issue(lastmod desc);
    create index issue_priority         on issue(priority);
    create index issue_state            on issue(state);
    create index issue_subject          on issue(subject);
    create index issue_uid_acl          on issue(uid, acl);
    create index issue_t0t1             on issue(t0, t1);
    create index issue_t1               on issue(t1);

-- drop table if exists sentry;
    create sequence sentry_seq start with 1000;
    create table sentry(
        id          bigint primary key default nextval('sentry_seq'),
        priority    int not null default 0,
        flags       int not null default 0,
        state       int not null default 0,
        
        t0          date,
        t1          date,           -- deadline
        
        doc         int not null,
        art         int not null,
        --batch       varchar(30) not null default '',
        --divs        varchar(100),
        resale      boolean not null default false,
        olabel      varchar(30),    -- label override
        ospec       varchar(30),    -- spec override
        
        qty         numeric(20,2) not null,
        price       numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0,   -- cache
        comment     varchar(200),
        footnote    varchar(200),
        
        constraint sentry_fk_art    foreign key(art)
            references art(id)          on update cascade on delete set null,
        constraint sentry_fk_doc    foreign key(doc)
            references issue(id)         on update cascade on delete cascade
    );

