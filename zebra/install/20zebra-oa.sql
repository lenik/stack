-- CONTENTS:
--      group, user
--      contact, org, person
--      diary
--      fileinfo
--      topic, reply
--      account, acdoc, acentry

-- drop table if exists "group";
    create sequence group_seq start with 1000;
    create table "group"(
        id          int primary key default nextval('group_seq'),
        code        varchar(30),
        label       varchar(40),
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        admin       boolean not null default false,
        
        constraint group_uk_code unique(code)
    );

    create index group_label        on "group"(label);
    create index group_lastmod      on "group"(lastmod desc);
    create index group_state        on "group"(state);

-- drop table if exists "user";
    create sequence user_seq start with 1000;
    create table "user"(
        id          int primary key default nextval('user_seq'),
        code        varchar(30),
        label       varchar(40),
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        gid0        int,            -- primary gid, 0 for root user.
        email       varchar(30),    -- some users don't have an email, like admin, guest.
        emailok     boolean not null default false,
        salt        int not null default random() * 1000000000,
        passwd      varchar(40) not null default '', 
        lastlog     timestamp,
        lastlogip   inet,
        
        constraint user_uk_code     unique(code),
        constraint user_fk_gid0     foreign key(gid0)
            references "group"(id)      on update cascade on delete set null
    );

    create index user_label         on "user"(label);
    create index user_lastmod       on "user"(lastmod desc);
    create index user_state         on "user"(state);

-- drop table if exists group_user;
    create table group_user(
        "group"     int not null,
        "user"      int not null,
        
        constraint group_user_pkey      primary key("group", "user"),
        constraint group_user_fk_group  foreign key("group")
            references "group"(id)          on update cascade on delete cascade,
        constraint group_user_fk_user   foreign key("user")
            references "user"(id)           on update cascade on delete cascade
    );

    insert into "group"(id, code, label, admin) values(0, 'root', 'Root', true);
    insert into "user"(id, gid0, code, label) values(0, 0, 'root', 'Root');
    insert into "group_user"("group", "user") values(0, 0);

-- drop table if exists contact;
    create sequence contact_seq start with 1000;
    create table contact(
        _id         int primary key default nextval('contact_seq'),
        org         int,
        ou          int,
        person      int,
        rename      varchar(80),        -- name override.
        
        usage       varchar(10),
        region      int,
        country     varchar(2) not null default 'cn',
        r1          varchar(30),
        r2          varchar(30),
        r3          varchar(30),
        address1    varchar(80),
        address2    varchar(80),
        postcode    varchar(8),
        
        tel         varchar(20),
        mobile      varchar(20),
        fax         varchar(20),
        email       varchar(30),
        web         varchar(80),
        qq          varchar(20),
        
        constraint contact_uk       unique(org, ou, person, usage)
    );

    create index contact_region     on contact(region);

-- drop table if exists org;
    create sequence org_seq start with 1000;
    create table org(
        id          int primary key default nextval('org_seq'),
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
        mode        int not null default b'110100100'::int,
        acl         int,

        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    varchar(40),
        peer        boolean not null default false,
        customer    boolean not null default false,
        supplier    boolean not null default false,
        subject     varchar(200),
        contact     int,
        bank        varchar(50),
        bankacc     varchar(30),
        
        size        int not null default 0,
        taxid       varchar(20),
        shipper     boolean not null default false,
        
        constraint org_uk_taxid     unique(taxid),
        constraint org_fk_contact   foreign key(contact)
            references contact(_id)     on update cascade on delete set null,
        constraint org_fk_uid       foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint org_fk_gid       foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index org_code           on org(code);
    create index org_label          on org(label);
    create index org_lastmod        on org(lastmod desc);
    create index org_priority       on org(priority);
    create index org_state          on org(state);
    create index org_uid_acl        on org(uid, acl);

-- drop table if exists orgunit;
    create sequence orgunit_seq start with 1000;
    create table orgunit(
        id          int primary key default nextval('orgunit_seq'),
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
        mode        int not null default b'110100100'::int,
        acl         int,
        
        org         int not null,
        parent      int,
        depth       int not null default -1,
        contact     int,
        
        constraint orgunit_fk_org   foreign key(org)
            references org(id)          on update cascade on delete cascade,
        constraint orgunit_fk_parent foreign key(parent)
            references orgunit(id)      on update cascade on delete cascade,
        constraint orgunit_fk_contact foreign key(contact)
            references contact(_id)     on update cascade on delete set null,
        constraint orgunit_fk_uid   foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint orgunit_fk_gid   foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index orgunit_code       on orgunit(code);
    create index orgunit_label      on orgunit(label);
    create index orgunit_lastmod    on orgunit(lastmod desc);
    create index orgunit_priority   on orgunit(priority);
    create index orgunit_state      on orgunit(state);
    create index orgunit_uid_acl    on orgunit(uid, acl);
    
-- drop table if exists person;
    create sequence person_seq start with 1000;
    create table person(
        id          int primary key default nextval('person_seq'),
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
        mode        int not null default b'110100100'::int,
        acl         int,
        
        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    varchar(40),
        peer        boolean not null default false,
        customer    boolean not null default false,
        supplier    boolean not null default false,
        subject     varchar(200),
        contact     int,
        bank        varchar(50),
        bankacc     varchar(30),

        employee    boolean not null default false,
        gender      char,
        homeland    varchar(10),
        passport    varchar(20),
        ssn         varchar(20),            -- social security number
        dln         varchar(20),            -- driver's license number
        
        constraint person_uk_ssn    unique(ssn),
        constraint person_fk_contact foreign key(contact)
            references contact(_id)     on update cascade on delete set null,
        constraint person_fk_uid    foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint person_fk_gid    foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index person_code        on person(code);
    create index person_label       on person(label);
    create index person_lastmod     on person(lastmod desc);
    create index person_priority    on person(priority);
    create index person_state       on person(state);
    create index person_uid_acl     on person(uid, acl);
    
    alter table contact add constraint contact_fk_org foreign key(org)
            references org(id)      on update cascade on delete cascade deferrable;
    alter table contact add constraint contact_fk_ou foreign key(ou)
            references orgunit(id)  on update cascade on delete cascade deferrable;
    alter table contact add constraint contact_fk_person foreign key(person)
            references person(id)   on update cascade on delete cascade deferrable;

-- drop table if exists personrole;
    create sequence personrole_seq;
    create table personrole(
        id          int primary key default nextval('personrole_seq'),
        org         int not null,
        ou          int,
        person      int not null,
        role        varchar(20),
        description varchar(200),
        
        constraint personrole_uk        unique(org, ou, person),
        constraint personrole_fk_person foreign key(person)
            references person(id)           on update cascade on delete cascade,
        constraint personrole_fk_ou     foreign key(ou)
            references orgunit(id)          on update cascade on delete cascade,
        constraint personrole_fk_org    foreign key(org)
            references org(id)              on update cascade on delete cascade
    );

-- drop table if exists diary;
    create sequence diary_seq start with 1000;
    create table diary(
        id          int primary key default nextval('diary_seq'),
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
        
        t0          date,           -- begin time, deliver time
        t1          date,           -- end time, deadline
        year        int not null default 0,
        
        op          int,
        subject     varchar(200) not null,
        text        text,
        form        int,
        args        text,           -- used with the form.
        cat         int,
        phase       int,
        
        constraint diary_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint diary_fk_form    foreign key(form)
            references form(id)         on update cascade on delete set null,
        constraint diary_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint diary_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint diary_fk_phase   foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint diary_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null
    );

    create index diary_lastmod        on diary(lastmod desc);
    create index diary_priority       on diary(priority);
    create index diary_state          on diary(state);
    create index diary_subject        on diary(subject);
    create index diary_t0t1           on diary(t0, t1);
    create index diary_t1             on diary(t1);
    create index diary_uid_acl        on diary(uid, acl);

-- drop table if exists fileinfo;
    create sequence fileinfo_seq start with 1000;
    create table fileinfo(          -- file info
        id          int primary key default nextval('fileinfo_seq'),
        label       varchar(80),    -- null if filename is used.
        description varchar(200), 
        
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
        
        t0          date,           -- validate since
        t1          date,           -- expire after
        
        op          int,
        cat         int,
        phase       int,
        nvote       int not null default 0,
        nlike       int not null default 0,
        ndl         int not null default 0, -- downloads
        
        dir         varchar(200) not null,
        base        varchar(80) not null,
        image       varchar(100),
        size        bigint not null,
        sha1        varchar(40),    
        type        varchar(100),   -- auto detected (file)
        encoding    varchar(30),    -- auto detected
        
        org         int,
        person      int,
        val         double precision, -- estimated
        
        constraint fileinfo_uk      unique(dir, base),
        constraint fileinfo_fk_cat  foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint fileinfo_fk_gid  foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint fileinfo_fk_op   foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint fileinfo_fk_org  foreign key(org)
            references org(id)          on update cascade,
        constraint fileinfo_fk_person foreign key(person)
            references person(id)       on update cascade,
        constraint fileinfo_fk_phase foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint fileinfo_fk_uid  foreign key(uid)
            references "user"(id)       on update cascade on delete set null
    );
    
    create index fileinfo_base          on fileinfo(base);
    create index fileinfo_lastmod       on fileinfo(lastmod desc);
    create index fileinfo_priority_nvote on fileinfo(priority, nvote desc);
    create index fileinfo_state         on fileinfo(state);
    create index fileinfo_t0t1          on fileinfo(t0, t1);
    create index fileinfo_t1            on fileinfo(t1);
    create index fileinfo_uid_acl       on fileinfo(uid, acl);

-- drop table if exists fileatt;
    create sequence fileatt_seq;
    create table fileatt(
        id          int primary key default nextval('fileatt_seq'),
        file        int not null,
        att         int not null,
        val         varchar(200),

        constraint fileatt_uk       unique(file, att),
        constraint fileatt_fk_file  foreign key(file)
            references fileinfo(id)     on update cascade on delete cascade,
        constraint fileatt_fk_att   foreign key(att)
            references att(id)          on update cascade on delete cascade
    );
    
-- drop table if exists filetag;
    create sequence filetag_seq;
    create table filetag(
        id          int primary key default nextval('filetag_seq'),
        file        int not null,
        tag         int not null,

        constraint filetag_fk_file  foreign key(file)
            references fileinfo(id)     on update cascade on delete cascade,
        constraint filetag_fk_tag   foreign key(tag)
            references tag(id)          on update cascade on delete cascade
    );

-- drop table if exists filevote;
    create sequence filevote_seq;
    create table filevote(
        id          int primary key default nextval('filevote_seq'),
        file        int not null,
        op          int not null,
        n           int not null default 1,
        time        timestamp not null default now(),

        constraint filevote_fk_file foreign key(file)
            references fileinfo(id)     on update cascade on delete cascade,
        constraint filevote_fk_op foreign key(op)
            references "user"(id)       on update cascade on delete cascade
    );
    
-- drop table if exists topic;
    create sequence topic_seq start with 1000;
    create table topic(
        id          int primary key default nextval('topic_seq'),
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
        
        t0          date,           -- begin time, deliver time
        t1          date,           -- end time, deadline
        year        int not null default 0,
        
        op          int,
        subject     varchar(200) not null,
        text        text,
        form        int,
        args        text,           -- used with the form.
        cat         int,
        phase       int,
        
        nread       int not null default 0,
        nvote       int not null default 0,
        nlike       int not null default 0,
        
        val         double precision not null default 0, -- estimated
        -- valmax   double precision,
        
        constraint topic_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint topic_fk_form    foreign key(form)
            references form(id)         on update cascade on delete set null,
        constraint topic_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint topic_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint topic_fk_phase   foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint topic_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null
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

-- drop table if exists reply;
    create sequence reply_seq;
    create table reply(
        id          int primary key default nextval('reply_seq'),
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
        
        t0          timestamptz,    -- work begin time
        t1          timestamptz,    -- work end time
        year        int not null default 0,
        
        op          int not null,
        text        text not null,
        cat         int,            -- reserved for general purpose.
        phase       int,            -- reserved for general purpose.
        nvote       int not null default 0,
        
        topic       int not null,
        parent      int,
        changes     text[],
        
        constraint reply_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint reply_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint reply_fk_topic   foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint reply_fk_parent  foreign key(parent)
            references reply(id)        on update cascade on delete set null,
        constraint reply_fk_phase   foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint reply_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint reply_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index reply_lastmod      on reply(lastmod desc);
    create index reply_priority     on reply(priority);
    create index reply_state        on reply(state);
    create index reply_t0t1         on reply(t0, t1);
    create index reply_t1           on reply(t1);
    create index reply_uid_acl      on reply(uid, acl);

-- drop table if exists topicparty;
    create sequence topicparty_seq;
    create table topicparty(
        id          bigint primary key default nextval('topicparty_seq'),
        topic       int not null,
        person      int,
        org         int,
        description varchar(60),
        
        constraint topicparty_fk_topic  foreign key(topic)
            references topic(id)            on update cascade on delete set null,
        constraint topicparty_fk_person foreign key(person)
            references person(id)           on update cascade on delete set null,
        constraint topicparty_fk_org    foreign key(org)
            references org(id)              on update cascade on delete set null
    );

-- drop table if exists topicatt;
    create sequence topicatt_seq;
    create table topicatt(
        id          int primary key default nextval('topicatt_seq'),
        topic       int not null,
        att         int not null,
        val         varchar(200),

        constraint topicatt_uk      unique(topic, att),
        constraint topicatt_fk_topic foreign key(topic)
            references topic(id)        on update cascade on delete cascade,
        constraint topicatt_fk_att  foreign key(att)
            references att(id)          on update cascade on delete cascade
    );

-- drop table if exists topictag;
    create sequence topictag_seq;
    create table topictag(
        id          int primary key default nextval('topictag_seq'),
        topic       int not null,
        tag         int not null,

        constraint topictag_fk_topic foreign key(topic)
            references topic(id)        on update cascade on delete cascade,
        constraint topictag_fk_tag  foreign key(tag)
            references tag(id)          on update cascade on delete cascade
    );

-- drop table if exists topicvote;
    create sequence topicvote_seq;
    create table topicvote(
        id          int primary key default nextval('topicvote_seq'),
        topic       int not null,
        op          int not null,
        n           int not null default 1,
        time        timestamp not null default now(),

        constraint topicvote_fk_topic foreign key(topic)
            references topic(id)        on update cascade on delete cascade,
        constraint topicvote_fk_op foreign key(op)
            references "user"(id)       on update cascade on delete cascade
    );

-- drop table if exists replyparty;
    create sequence replyparty_seq;
    create table replyparty(
        id          int primary key default nextval('replyparty_seq'),
        reply       int not null,
        person      int,
        org         int,
        description varchar(60),
        
        constraint replyparty_fk_reply  foreign key(reply)
            references reply(id)            on update cascade on delete set null,
        constraint replyparty_fk_person foreign key(person)
            references person(id)           on update cascade on delete set null,
        constraint replyparty_fk_org    foreign key(org)
            references org(id)              on update cascade on delete set null
    );

-- drop table if exists replyatt;
    create sequence replyatt_seq;
    create table replyatt(
        id          int primary key default nextval('replyatt_seq'),
        reply       int not null,
        att         int not null,
        val         varchar(200),

        constraint replyatt_uk      unique(reply, att),
        constraint replyatt_fk_reply foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replyatt_fk_att  foreign key(att)
            references att(id)          on update cascade on delete cascade
    );

-- drop table if exists replytag;
    create sequence replytag_seq;
    create table replytag(
        id          int primary key default nextval('replytag_seq'),
        reply       int not null,
        tag         int not null,

        constraint replytag_fk_reply foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replytag_fk_tag  foreign key(tag)
            references tag(id)          on update cascade on delete cascade
    );

-- drop table if exists replyvote;
    create sequence replyvote_seq;
    create table replyvote(
        id          int primary key default nextval('replyvote_seq'),
        reply       int not null,
        op          int not null,
        n           int not null default 1,
        time        timestamp not null default now(),

        constraint replyvote_fk_reply foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replyvote_fk_op foreign key(op)
            references "user"(id)       on update cascade on delete cascade
    );

-- drop table if exists "account";
    create table "account"(
        id          int primary key,
        label       varchar(20) not null,
        description varchar(200),
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        sign        int not null default 0
    );

    create index account_label      on account(label);
    create index account_lastmod    on account(lastmod desc);
    create index account_priority   on account(priority);
    create index account_state      on account(state);

-- drop table if exists acdoc;
    create sequence acdoc_seq start with 1000;
    create table acdoc(
        id          int primary key default nextval('acdoc_seq'),
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
        
        t0          date,           -- accounting date range
        t1          date,           -- accounting date range
        year        int not null default 0, -- same year of t0.
        
        op          int,
        subject     varchar(200) not null,
        text        text,
        form        int,
        args        text,           -- used with the form.
        cat         int,
        phase       int,
        
        prev        int,            -- previous doc
        topic       int,
        org         int,
        person      int,
        
        ndebit      double precision not null default 0,
        ncredit     double precision not null default 0,
        
        constraint acdoc_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint acdoc_fk_form    foreign key(form)
            references form(id)         on update cascade on delete set null,
        constraint acdoc_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint acdoc_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint acdoc_fk_org     foreign key(org)
            references org(id)          on update cascade,
        constraint acdoc_fk_person  foreign key(person)
            references person(id)       on update cascade,
        constraint acdoc_fk_phase   foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint acdoc_fk_prev    foreign key(prev)
            references acdoc(id)        on update cascade on delete set null,
        constraint acdoc_fk_topic   foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint acdoc_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null

    );

    create index acdoc_lastmod         on acdoc(lastmod desc);
    create index acdoc_priority        on acdoc(priority);
    create index acdoc_state           on acdoc(state);
    create index acdoc_subject         on acdoc(subject);
    create index acdoc_uid_acl         on acdoc(uid, acl);
    create index acdoc_year            on acdoc(year);
    create index acdoc_t0t1            on acdoc(t0, t1);
    create index acdoc_t1              on acdoc(t1);

-- drop table if exists acentry;
    create sequence acentry_seq start with 1000;
    create table acentry(
        id          bigint primary key default nextval('acentry_seq'),
        priority    int not null default 0,
        flags       int not null default 0,
        state       int not null default 0,
        
        doc         int not null,
        account     int not null,
        org         int,
        person      int,
        -- positive for the debit side, or negative for the credit side.
        val         numeric(20,2) not null default 0,
        
        constraint acentry_fk_account foreign key(account)
            references account(id)      on update cascade,
        constraint acentry_fk_doc   foreign key(doc)
            references acdoc(id)        on update cascade on delete cascade,
        constraint acentry_fk_org   foreign key(org)
            references org(id)          on update cascade on delete set null,
        constraint acentry_fk_person foreign key(person)
            references person(id)       on update cascade on delete set null
    );

-- drop table if exists acinit;
    create sequence acinit_seq start with 1000;
    create table acinit(
        id          int primary key default nextval('acinit_seq'),
        year        int not null,
        account     int not null,
        org         int,
        person      int,
        val         numeric(20,2) not null default 0,
        
        constraint acinit_uk_code   unique(year, account, org, person),
        
        constraint acinit_fk_account foreign key(account)
            references account(id)      on update cascade,
        constraint acinit_fk_org    foreign key(org)
            references org(id)          on update cascade on delete set null,
        constraint acinit_fk_person foreign key(person)
            references person(id)       on update cascade on delete set null
    );
    
