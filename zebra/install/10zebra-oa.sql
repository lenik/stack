-- CONTENTS:
--      group, user
--      contact, org, person
--      fileinfo
--      topic, reply
--      account, acdoc, acentry

-- builtin data

    insert into schema(id, code, label) values(1, 'post',   'Topic Post');
    insert into schema(id, code, label) values(2, 'reply',  'Topic Reply');
    insert into schema(id, code, label) values(3, 'opp',    'Opportunity');
    insert into schema(id, code, label) values(4, 'trk',    'Opportunity Tracking');
    insert into schema(id, code, label) values(5, 'ques',   'Question');
    insert into schema(id, code, label) values(6, 'ans',    'Answer');
    insert into schema(id, code, label) values(7, 'wlog',   'Work Log');
    insert into schema(id, code, label) values(8, 'acc',    'Accounting');
    insert into schema(id, code, label) values(9, 'file',   'File Info');
    
    insert into cat(id, schema, code, label, description) values(301, 3, 'opp', '机会', '潜在的或已实现的销售机会');
    insert into cat(id, schema, code, label, description) values(302, 3, 'job', '任务', '指派的工作任务');
    insert into cat(id, schema, code, label, description) values(401, 4, 'trk', '跟进', '机会/项目的跟进');
    insert into cat(id, schema, code, label, description) values(701, 7, 'log', '日志', '记录工作的内容');
    insert into cat(id, schema, code, label, description) values(702, 7, 'plan', '计划', '计划将来要做的事情');

    insert into phase(id, schema, code, label, priority)
        values(1, 3, 'init', '初步信息采集', 10);
    insert into phase(id, schema, code, label, priority)
        values(2, 3, 'chat', '初步沟通', 20);
    insert into phase(id, schema, code, label, priority)
        values(3, 3, 'meet', '交涉中/走访中', 30);
    insert into phase(id, schema, code, label, priority)
        values(4, 3, 'well', '已确定意向/已报价', 40);
    insert into phase(id, schema, code, label, priority)
        values(5, 3, 'paid', '合同签订/已付款', 50);
    
    insert into att(id, schema, code, label)
        values(1, 3, 'addr', '地址');
    insert into att(id, schema, code, label)
        values(2, 4, 'cost', '开支');
    
    insert into tagv(id, schema, code, label) values(1, 3, 'JHLY', '机会来源');
    insert into tag(tagv, code, label) values(1, 'tel',     '电话来访');
    insert into tag(tagv, code, label) values(1, 'friend',  '朋友介绍');
    insert into tag(tagv, code, label) values(1, 'client',  '客户介绍');
    insert into tag(tagv, code, label) values(1, 'indep',   '独立开发');
    insert into tag(tagv, code, label) values(1, 'media',   '媒体宣传');
    insert into tag(tagv, code, label) values(1, 'promote', '促销活动');
    insert into tag(tagv, code, label) values(1, 'resell',  '老客户');
    insert into tag(tagv, code, label) values(1, 'agent',   '代理商');
    insert into tag(tagv, code, label) values(1, 'tagvner', '合作伙伴');
    insert into tag(tagv, code, label) values(1, 'bid',     '公开招标');
    insert into tag(tagv, code, label) values(1, 'inet',    '互联网');

    insert into tagv(id, schema, code, label) values(2, 4, 'QTLX', '洽谈类型');
    insert into tag(tagv, code, label) values(2, 'tel',     '电话洽谈');
    insert into tag(tagv, code, label) values(2, 'f2f',     '面谈');
    insert into tag(tagv, code, label) values(2, 'walk',    '走访');
    insert into tag(tagv, code, label) values(2, 'inet',    '通过互联网');
    insert into tag(tagv, code, label) values(2, 'mail',    '信件洽谈');

    insert into tagv(id, schema, code, label) values(3, 3, 'GHFS', '供货方式');
    insert into tag(tagv, code, label) values(3, 'JDJG',    '甲订甲供');
    insert into tag(tagv, code, label) values(3, 'JDYG',    '甲订乙供');
    insert into tag(tagv, code, label) values(3, 'YDYG',    '乙订乙供');
    
    insert into tagv(id, schema, code, label) values(4, 3, 'CGYZ', '采购原则');
    insert into tag(tagv, code, label) values(4, 'PPYX',    '品牌优先');
    insert into tag(tagv, code, label) values(4, 'JGYX',    '价格优先');
    insert into tag(tagv, code, label) values(4, 'XJBYX',   '性价比优先');

    insert into tagv(id, schema, code, label) values(5, 9, 'WJXX', '文件信息');
    
    insert into rlock(schema)   select id from schema   where id<1000;
    insert into rlock(cat)      select id from cat      where id<1000;
    insert into rlock(phase)    select id from phase    where id<1000;
    insert into rlock(att)      select id from att      where id<1000;
    insert into rlock(tagv)     select id from tagv     where id<1000;
    insert into rlock(form)     select id from form     where id<1000;
    
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
        
        gid0        int,            -- primary gid, 0 for root user.
        email       varchar(30),    -- some users don't have an email, like admin, guest.
        emailok     boolean not null default false,
        salt        int not null default random() * 1000000000,
        passwd      varchar(40) not null default '', 
        lastlog     timestamp,
        lastlogip   inet,
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
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

    create or replace view v_group as
        select *, 
            array(select "user" from group_user a where a."group"=g.id) users,
            array(select coalesce(label, code) from group_user a left join "user" u on a."user"=u.id 
                where a."group"=g.id) labels
        from "group" g;

    create or replace view v_user as
        select u.*, g.label "label0",
            array(select "group" from group_user a where a."user"=u.id) groups,
            array(select coalesce(label, code) from group_user a left join "group" g on a."group"=g.id
                where a."user"=u.id) labels
        from "user" u left join "group" g on u.gid0 = g.id;
        
    insert into "group"(id, code, label) values(0, 'root', 'Root');
    insert into "user"(id, gid0, code, label) values(0, 0, 'root', 'Root');
    insert into "group_user"("group", "user") values(0, 0);

-- drop table if exists contact;
    create sequence contact_seq;
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
    create sequence org_seq;
    create table org(
        id          int primary key default nextval('org_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    int not null default 800,
        size        int not null default 0,
        customer    boolean not null default false,
        supplier    boolean not null default false,
        subject     varchar(200),
        comment     varchar(200),
        contact     int,
        bank        varchar(50),
        bankacc     varchar(30),
        taxid       varchar(20),
        
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
    create sequence orgunit_seq;
    create table orgunit(
        id          int primary key default nextval('orgunit_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        org         int not null,
        parent      int,
        depth       int not null default -1,
        contact     int,
        
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
    create sequence person_seq;
    create table person(
        id          int primary key default nextval('person_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        birthday    date,
        locale      varchar(10) not null default 'zh-cn',
        timezone    int not null default 800,
        gender      char,
        employee    boolean not null default false,
        homeland    varchar(10),
        passport    varchar(20),
        ssn         varchar(20),            -- social security number
        dln         varchar(20),            -- driver's license number
        
        customer    boolean not null default false,
        supplier    boolean not null default false,
        subject     varchar(200),
        comment     varchar(200),
        contact     int,
        bank        varchar(50),
        bankacc     varchar(30),
        
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

    create or replace view v_contact as
        select org.label "org_label", 
            oo.label || ' - ' || ou.label "ou_label",
            p.label "person_label", c.*
        from contact c
            left join org           on c.org=org.id
            left join orgunit ou    on c.ou=ou.id
            left join org oo        on ou.org=oo.id
            left join person p      on c.person=p.id;

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

    create or replace view v_personrole as
        select o.label "org_label", ou.label "ou_label", p.label "person_label", r.*
        from personrole r
            left join person p on r.person=p.id
            left join orgunit ou on r.ou=ou.id
            left join org o on r.org=o.id;

-- drop table if exists fileinfo;
    create sequence fileinfo_seq start with 1000;
    create table fileinfo(          -- file info
        id          int primary key default nextval('fileinfo_seq'),
        code        varchar(40),
        label       varchar(80),    -- null if filename is used.
        description varchar(200), 
        path        varchar(200) not null,
        image       varchar(100),
        
        size        bigint not null,
        sha1        varchar(32),    
        type        varchar(100),   -- auto detected
        encoding    varchar(100),   -- auto detected
        
        op          int,
        org         int,
        person      int,
        tags        int[],
        
        nvote       int not null default 0,
        nfav        int not null default 0,
        ndl         int not null default 0, -- downloads
        
        val         double precision, -- estimated
        t0          date,           -- validate since
        t1          date,           -- expire after
        
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
        
        constraint fileinfo_fk_gid  foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint fileinfo_fk_op   foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint fileinfo_fk_org  foreign key(org)
            references org(id)          on update cascade,
        constraint fileinfo_fk_person foreign key(person)
            references person(id)       on update cascade,
        constraint fileinfo_fk_uid  foreign key(uid)
            references "user"(id)       on update cascade on delete set null
    );
    
    create index fileinfo_lastmod        on fileinfo(lastmod desc);
    create index fileinfo_priority_nvote on fileinfo(priority, nvote desc);
    create index fileinfo_state          on fileinfo(state);
    create index fileinfo_t0t1           on fileinfo(t0, t1);
    create index fileinfo_t1             on fileinfo(t1);
    create index fileinfo_uid_acl        on fileinfo(uid, acl);

-- drop table if exists filevote;
    create sequence filevote_seq;
    create table filevote(
        id          int primary key default nextval('filevote_seq'),
        file        int not null,
        op          int not null,
        n           int not null default 1,

        constraint filevote_fk_file foreign key(file)
            references fileinfo(id)     on update cascade on delete cascade,
        constraint filevote_fk_op foreign key(op)
            references "user"(id)       on update cascade on delete cascade
    );

-- drop table if exists topic;
    create sequence topic_seq start with 1000;
    create table topic(
        id          int primary key default nextval('topic_seq'),
        op          int,
        subject     varchar(200) not null,
        text        text,
        nread       int not null default 0,
        nvote       int not null default 0,
        nfav        int not null default 0,
        
        cat         int,
        phase       int,
        val         double precision not null default 0, -- estimated
        -- valmax   double precision,
        
        t0          date,           -- begin time, deliver time
        t1          date,           -- end time, deadline
        
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
        
        constraint topic_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
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

-- drop table if exists reply;
    create sequence reply_seq;
    create table reply(
        id          int primary key default nextval('reply_seq'),
        op          int not null,
        text        text not null,
        nvote       int not null default 0,
        
        topic       int not null,
        parent      int,
        changes     text[],
        
        t0          timestamptz,    -- work begin time
        t1          timestamptz,    -- work end time
        
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
        
        constraint reply_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint reply_fk_topic   foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint reply_fk_parent  foreign key(parent)
            references reply(id)        on update cascade on delete set null,
        constraint reply_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint reply_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index reply_lastmod        on reply(lastmod desc);
    create index reply_priority       on reply(priority);
    create index reply_state          on reply(state);
    create index reply_t0t1           on reply(t0, t1);
    create index reply_t1             on reply(t1);
    create index reply_uid_acl        on reply(uid, acl);

-- drop table if exists topicparty;
    create sequence topicparty_seq;
    create table topicparty(
        id          int primary key default nextval('topicparty_seq'),
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

        constraint replyvote_fk_reply foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replyvote_fk_op foreign key(op)
            references "user"(id)       on update cascade on delete cascade
    );

-- aggregated views
    create or replace view v_topic as
        select *, 
            array(select tag || ':' || tag.label
                from topictag a left join tag on a.tag=tag.id where a.topic=topic.id) tags,
            array(select att || ':' || att.label || '=' || a.val
                from topicatt a left join att on a.att=att.id where a.topic=topic.id) atts
        from topic;

    create or replace view v_reply as
        select *, 
            array(select tag || ':' || tag.label
                from replytag a left join tag on a.tag=tag.id where a.reply=reply.id) tags,
            array(select att || ':' || att.label || '=' || a.val
                from replyatt a left join att on a.att=att.id where a.reply=reply.id) atts
        from reply;

    insert into topic(id, subject, text, cat)
        values(1, '未分类（机会）', '未指定机会的行动集', 401);

-- drop table if exists "account";
    create table "account"(
        id          int primary key,
        label       varchar(20) not null,
        description varchar(200),
        sign        int not null default 0,
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0
    );

    create index account_label      on account(label);
    create index account_lastmod    on account(lastmod desc);
    create index account_priority   on account(priority);
    create index account_state      on account(state);

-- drop table if exists acdoc;
    create sequence acdoc_seq start with 1000;
    create table acdoc(
        id          bigint primary key default nextval('acdoc_seq'),
        prev        bigint,         -- previous doc
        form        int,
        subject     varchar(200) not null,
        text        text,
        args        text,           -- used with the form.
        
        op          int,
        org         int,
        person      int,
        
        cat         int,
        phase       int,
        val         double precision not null default 0,
        
        year        int not null default 0, -- same year of t0.
        t0          date,           -- accounting date range
        t1          date,           -- accounting date range

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
        doc         bigint not null,
        
        account     int not null,
        org         int,
        person      int,
        -- positive for the debit side, or negative for the credit side.
        val         numeric(20,2) not null default 0,
        
        priority    int not null default 0,
        
        constraint acentry_fk_account foreign key(account)
            references account(id)      on update cascade,
        constraint acentry_fk_doc   foreign key(doc)
            references acdoc(id)        on update cascade on delete cascade,
        constraint acentry_fk_org   foreign key(org)
            references org(id)          on update cascade on delete set null,
        constraint acentry_fk_person foreign key(person)
            references person(id)       on update cascade on delete set null
    );

    create or replace view v_acentry as
        select a.id, a.doc, d.subject,
            a.account, c.label "account_label",
            a.org, o.label "org_label",
            a.person, p.label "person_label",
            a.val, d.val "doc_val", a.priority,
            d.subject "doc_subject", d.cat, d.phase,
            d.year, d.t0, d.t1, d.creation, d.lastmod
        from acentry a
            left join account c on a.account=c.id
            left join acdoc d on a.doc=d.id
            left join org o on a.org=o.id
            left join person p on a.person=p.id;

    create or replace view v_acdoc as
        select a.*, op.label "op_label", o.label "org_label", p.label "person_label",
            array(select e.account_label || '=' || e.val from v_acentry e where e.doc=a.id) "entries"
        from acdoc a
            left join org o on a.org=o.id
            left join person p on a.person=p.id
            left join "user" op on a.op=op.id;

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
    
    create view v_acinits as
        select year, account, sum(val) from acinit group by year, account;
    
    create view v_acinit as
        select a.id, a.year,
            a.account, c.label "account_label",
            a.org, o.label "org_label",
            a.person, p.label "person_label",
            a.val
        from acinit a
            left join account c on a.account=c.id
            left join org o on a.org=o.id
            left join person p on a.person=p.id;

