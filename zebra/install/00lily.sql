-- drop table if exists schema;
    create sequence schema_seq start with 100;
    create table schema(
        id          int primary key default nextval('schema_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint schema_uk_code unique(code)
    );

-- drop table if exists tagv;
    create sequence tagv_seq start with 100;
    create table tagv(
        id          int primary key default nextval('tagv_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        schema      int not null,
        topic       boolean not null default false,
        reply       boolean not null default false,
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint tagv_uk_code      unique(code)
    );

    create index tagv_priority       on tagv(priority);
    create index tagv_lastmod        on tagv(lastmod desc);
    create index tagv_state          on tagv(state);
    
-- drop table if exists phase;
    create sequence phase_seq start with 100;
    create table phase(
        id          int primary key default nextval('phase_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint phase_uk_code      unique(code)
    );

    create index phase_label          on phase(label);
    
-- drop table if exists cat;
    create sequence cat_seq start with 1000;
    create table cat(
        id          int primary key default nextval('cat_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint cat_uk_code      unique(code)
    );

    create index cat_label          on cat(label);

-- drop table if exists att;
    create sequence att_seq start with 100;
    create table att(
        id          int primary key default nextval('att_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint att_uk_code      unique(code)
    );

    create sequence attval_seq;
    create table attval(
        id          int primary key default nextval('attval_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        att         int not null,
        val         text not null,
        
        priority    int not null default 0,
        
        constraint attval_uk_code   unique(code),
        constraint attval_fk_att    foreign key(att)
            references att(id)      on update cascade on delete cascade
    );
    
    create index attval_label       on attval(label);
    create index attval_priority    on attval(priority);
    
    create or replace view v_att as
        select *, array(select val from attval a where a.att = att.id) "vals" from att;
    
-- drop table if exists tag;
    create sequence tag_seq start with 100;
    create table tag(
        id          int primary key default nextval('tag_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        tagv        int not null,
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint tag_uk_code      unique(tagv, code),
        constraint tag_fk_tagv      foreign key(tagv)
            references tagv(id)     on update cascade on delete cascade
    );

    create index tag_label          on tag(label);
    create index tag_priority       on tag(priority);
    create index tag_lastmod        on tag(lastmod desc);
    create index tag_state          on tag(state);
    
