    drop view v_att;
    drop table tag;
    drop table tagv;
    drop table attval;
    drop table att;
    drop table cat;
    drop table phase;
    drop table schema;

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
        
        priority    int not null default 0,
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint phase_uk_code      unique(code)
    );

    create index phase_label          on phase(label);
    create index phase_priority       on phase(priority);
    create index phase_lastmod        on phase(lastmod desc);
    create index phase_state          on phase(state);
    
-- drop table if exists cat;
    create sequence cat_seq start with 100;
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
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        
        constraint attval_uk_code   unique(code),
        constraint attval_fk_att    foreign key(att)
            references att(id)      on update cascade on delete cascade
    );
    
    create index attval_label       on attval(label);
    create index attval_priority    on attval(priority);
    create index attval_lastmod     on attval(lastmod desc);
    create index attval_state       on attval(state);
    
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
    
    insert into schema(id, code, label) values(1, 'post', 'Topic Post');
    insert into schema(id, code, label) values(2, 'reply', 'Topic Reply');
    insert into schema(id, code, label) values(3, 'opp', 'Opportunity');
    insert into schema(id, code, label) values(4, 'act', 'Action Log');
    insert into schema(id, code, label) values(5, 'ques', 'Question');
    insert into schema(id, code, label) values(6, 'ans', 'Answer');
    
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
    
    insert into cat(id, schema, code, label, description) values(1, 3, 'opp', '机会', '潜在的或已实现的销售机会');
    insert into cat(id, schema, code, label, description) values(2, 3, 'job', '任务', '指派的工作任务');
    
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

