--\import zebra.oa.org
--\import zebra.oa.person

    create sequence fileinfo_seq start with 1000;
    create table fileinfo(          -- file info
        id          int primary key default nextval('fileinfo_seq'),
--\mixin lily.mixin.Acl_rw-r-----
--\mixin lily.mixin.LabelExVer
--\mixin lily.mixin.Mi

        op          int
            references "user"(id) on update cascade on delete set null,

        cat         int
            references _cat(id) on update cascade on delete set null,

        phase       int
            references _phase(id) on update cascade on delete set null,

        nvote       int not null default 0,
        nlike       int not null default 0,
        ndl         int not null default 0, -- downloads

        dir         varchar(200),
        base        varchar(80),
        constraint fileinfo_uk_dir_base unique(dir, base),

        image       varchar(100),
        size        bigint not null,
        sha1        varchar(40),
        type        varchar(100),   -- auto detected (file)
        encoding    varchar(30),    -- auto detected

        org         int
            references org(id) on update cascade,

        person      int
            references person(id) on update cascade,

        val         double precision  -- estimated
    );

    create index fileinfo_base          on fileinfo(base);
    create index fileinfo_lastmod       on fileinfo(lastmod desc);
    create index fileinfo_priority_nvote on fileinfo(priority, nvote desc);
    create index fileinfo_state         on fileinfo(state);
    create index fileinfo_t0t1          on fileinfo(t0, t1);
    create index fileinfo_t1            on fileinfo(t1);
    create index fileinfo_uid_acl       on fileinfo(uid, acl);

    create sequence file_parm_seq;
    create table file_parm(
        id          int primary key default nextval('file_parm_seq'),

        file        int not null
            references fileinfo(id) on update cascade on delete cascade,

        parm         int not null
            references _parm(id) on update cascade on delete cascade,

        val         varchar(200),

        constraint file_parm_uk unique(file, parm)
    );

-- drop table if exists filetag;
    create sequence filetag_seq;
    create table filetag(
        id          int primary key default nextval('filetag_seq'),

        file        int not null
            references fileinfo(id) on update cascade on delete cascade,

        tag         int not null
            references _tag(id) on update cascade on delete cascade
    );

-- drop table if exists filevote;
    create sequence filevote_seq;
    create table filevote(
        id          int primary key default nextval('filevote_seq'),

        file        int not null
            references fileinfo(id) on update cascade on delete cascade,

        op          int not null
            references "user"(id) on update cascade on delete cascade,

        n           int not null default 1,
        time        timestamp not null default now()
    );
