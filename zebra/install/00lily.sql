-- drop table if exists schema;
    create sequence schema_seq start with 1000;
    create table schema(
        id          int primary key default nextval('schema_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint schema_uk_code unique(code)
    );

-- drop table if exists cat;
    create sequence cat_seq start with 1000;
    create table cat(
        id          int primary key default nextval('cat_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint cat_uk_code      unique(code),
        constraint cat_fk_schema    foreign key(schema)
            references schema(id)       on update cascade on delete cascade
    );

    create index cat_label          on cat(label);
    create index cat_lastmod        on cat(lastmod desc);
    create index cat_priority       on cat(priority);
    create index cat_state          on cat(state);

-- drop table if exists phase;
    create sequence phase_seq start with 1000;
    create table phase(
        id          int primary key default nextval('phase_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint phase_uk_code    unique(code),
        constraint phase_fk_schema  foreign key(schema)
            references schema(id)       on update cascade on delete cascade
    );

    create index phase_label        on phase(label);
    create index phase_lastmod      on phase(lastmod desc);
    create index phase_priority     on phase(priority);
    create index phase_state        on phase(state);
    
-- drop table if exists att;
    create sequence att_seq start with 1000;
    create table att(
        id          int primary key default nextval('att_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        constraint att_uk_code      unique(code),
        constraint att_fk_schema    foreign key(schema)
            references schema(id)       on update cascade on delete cascade
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
    
-- drop table if exists tagv;
    create sequence tagv_seq start with 1000;
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
        
        constraint tagv_uk_code     unique(code),
        constraint tagv_fk_schema   foreign key(schema)
            references schema(id)       on update cascade on delete cascade
    );

    create index tagv_lastmod       on tagv(lastmod desc);
    create index tagv_priority      on tagv(priority);
    create index tagv_state         on tagv(state);
    
-- drop table if exists tag;
    create sequence tag_seq start with 1000;
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
    create index tag_lastmod        on tag(lastmod desc);
    create index tag_priority       on tag(priority);
    create index tag_state          on tag(state);
    
-- drop table if exists form;
    create sequence form_seq start with 1000;
    create table form(
        id          int primary key default nextval('form_seq'),
        schema      int not null,
        code        varchar(20),
        label       varchar(80),
        subject     varchar(200),
        text        text,
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint form_uk_code     unique(code),
        constraint form_fk_schema   foreign key(schema)
            references schema(id)       on update cascade on delete cascade
    );

    create index form_label          on form(label);
    create index form_lastmod        on form(lastmod desc);
    create index form_priority       on form(priority);
    create index form_state          on form(state);

-- drop table if exists formcp;
    create sequence formcp_seq start with 1000;
    create table formcp(            -- creation parameters
        id          int primary key default nextval('formcp_seq'),
        form        int not null,
        name        varchar(30),
        value       varchar(100),
        
        constraint formcp_uk        unique(form, name),
        constraint formcp_fk_form   foreign key(form)
            references form(id)         on update cascade on delete cascade
    );
    
    create or replace view v_form as
        select *, 
            array(select name || '=' || value from formcp p where p.form = form.id) "params"
        from form;

-- drop table if exists rlock;      -- reference lock
    create table rlock(
        schema      int,
        cat         int,
        phase       int,
        att         int,
        tagv        int,
        form        int,
        
        constraint rlock_uk_schema  unique(schema),
        constraint rlock_uk_cat     unique(cat),
        constraint rlock_uk_phase   unique(phase),
        constraint rlock_uk_att     unique(att),
        constraint rlock_uk_tagv    unique(tagv),
        constraint rlock_uk_form    unique(form),
        
        constraint rlock_schema foreign key(schema) references schema(id) on update cascade,
        constraint rlock_cat    foreign key(cat)    references    cat(id) on update cascade,
        constraint rlock_phase  foreign key(phase)  references  phase(id) on update cascade,
        constraint rlock_att    foreign key(att)    references    att(id) on update cascade,
        constraint rlock_tagv   foreign key(tagv)   references   tagv(id) on update cascade,
        constraint rlock_form   foreign key(form)   references   form(id) on update cascade
    );

