
    create table "user"(
        id varchar(30) not null primary key,
        name varchar(40),
        email varchar(30),
        
        salt int not null default trunc(random() * 10000),
        password varchar(40),
        
        description varchar(200),
        avatar bigint,
        
        creation timestamp without time zone not null
            default now(),
        lastmod timestamp without time zone not null
            default now(),
        state int not null default 0,
        uid int not null default 0,
        gid int not null default 0,
        mode int not null default 432, -- 0660
        acl int not null default 0
    );

    create index user_lastmod on "user" using btree(lastmod);
    create index user_uid on "user" using btree(uid);

    insert into "user"(id, full_name, mode) values(
        'admin', 'Administrator', 384); -- 0600

