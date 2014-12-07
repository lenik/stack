-- drop table if exists FOO;
    create sequence FOO_seq start with 100;
    create table FOO(
        id          int primary key default nextval('FOO_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        uid         int,
        gid         int,
        mode        int not null default 640,
        acl         int,
        
        subject     varchar(200) not null default '',
        text        text not null default '',
        
        constraint FOO_uk_code      unique(code),
        constraint FOO_fk_uid       foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint FOO_fk_gid       foreign key(gid)
            references "group"(id)  on update cascade on delete set null
    );

    create index FOO_label          on FOO(label);
    create index FOO_priority       on FOO(priority);
    create index FOO_lastmod        on FOO(lastmod desc);
    create index FOO_state          on FOO(state);
    create index FOO_uid_acl        on FOO(uid, acl);

