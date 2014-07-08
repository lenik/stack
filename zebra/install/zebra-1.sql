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

-- drop table if exists "group";
    create sequence group_seq start with 100;
    create table "group"(
        id          int primary key default nextval('group_seq'),
        code        varchar(30),
        label       varchar(40),
        description varchar(200),
        
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint group_uk_code unique(code)
    );

    create index group_label on "group"(label);
    create index group_lastmod on "group"(lastmod desc);
    create index group_state on "group"(state);

-- drop table if exists "user";
    create sequence user_seq start with 100;
    create table "user"(
        id          int primary key default nextval('user_seq'),
        code        varchar(30),
        label       varchar(40),
        description varchar(200),
        
        gid0        int,           -- primary gid, 0 for root user.
        email       varchar(30),  -- some users don't have an email, like admin, guest.
        emailok     boolean not null default false,
        salt        int not null default random() * 1000000000,
        passwd      varchar(40) not null default '', 
        lastlog     timestamp,
        lastlogip   inet,
        
        creation    timestamp not null default now(),
        lastmod     timestamp not null default now(),
        flags       int not null default 0,
        state       int not null default 0,
        version     int not null default 0,
        
        constraint user_uk_code     unique(code),
        constraint user_fk_gid0     foreign key(gid0)
            references "group"(id)  on update cascade on delete set null
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
            references "group"(id)      on update cascade on delete cascade,
        constraint group_user_fk_user   foreign key("user")
            references "user"(id)       on update cascade on delete cascade
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
        address1    varchar(80),
        address2    varchar(80),
        r1          varchar(30),
        r2          varchar(30),
        r3          varchar(30),
        country     varchar(2) not null default 'cn',
        postcode    varchar(8),
        
        tel         varchar(20),
        mobile      varchar(20),
        fax         varchar(20),
        email       varchar(30),
        web         varchar(80),
        qq          varchar(20)
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
        constraint org_fk_uid       foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint org_fk_gid       foreign key(gid)
            references "group"(id)  on update cascade on delete set null,
        constraint org_fk_contact   foreign key(contact)
            references contact(_id) on update cascade on delete set null
    );

    create index org_code           on org(code);
    create index org_label          on org(label);
    create index org_priority       on org(priority);
    create index org_lastmod        on org(lastmod desc);
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
            references org(id)      on update cascade on delete cascade,
        constraint orgunit_fk_parent foreign key(parent)
            references orgunit(id)  on update cascade on delete cascade,
        constraint orgunit_fk_uid   foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint orgunit_fk_gid   foreign key(gid)
            references "group"(id)  on update cascade on delete set null,
        constraint orgunit_fk_contact foreign key(contact)
            references contact(_id) on update cascade on delete set null
    );

    create index orgunit_code       on orgunit(code);
    create index orgunit_label      on orgunit(label);
    create index orgunit_priority   on orgunit(priority);
    create index orgunit_lastmod    on orgunit(lastmod desc);
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
        
        constraint person_uk_ssn        unique(ssn),
        constraint person_fk_uid        foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint person_fk_gid        foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint person_fk_contact    foreign key(contact)
            references contact(_id)     on update cascade on delete set null
    );

    create index person_code        on person(code);
    create index person_label       on person(label);
    create index person_priority    on person(priority);
    create index person_lastmod     on person(lastmod desc);
    create index person_state       on person(state);
    create index person_uid_acl     on person(uid, acl);
    
    alter table contact add constraint contact_fk_org foreign key(org)
            references org(id)      on update cascade on delete cascade;
    alter table contact add constraint contact_fk_ou foreign key(ou)
            references orgunit(id)  on update cascade on delete cascade;
    alter table contact add constraint contact_fk_person foreign key(person)
            references person(id)   on update cascade on delete cascade;

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
            references person(id)       on update cascade on delete cascade,
        constraint personrole_fk_ou     foreign key(ou)
            references orgunit(id)      on update cascade on delete cascade,
        constraint personrole_fk_org    foreign key(org)
            references org(id)          on update cascade on delete cascade
    );

    create or replace view v_personrole as
        select o.label "org_label", ou.label "ou_label", p.label "person_label", r.*
        from personrole r
            left join person p on r.person=p.id
            left join orgunit ou on r.ou=ou.id
            left join org o on r.org=o.id;
    
-- ENUMs

    create type CellUsage as enum(
        'GROUP', 'INTERNAL', 'SUPPLIER', 'CUSTOMER',
        'SUBQUALITY', 'WASTE');
    
    create type SupplyMethod as enum(
        'BUY', 'PRODUCE');

-- drop table if exists warehouse;
    create sequence warehouse_seq;
    create table warehouse(
        id          int primary key default nextval('warehouse_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
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
        
        constraint warehouse_uk_code    unique(code),
        constraint warehouse_fk_contact foreign key(contact)
            references contact(_id)     on update cascade on delete set null,
        constraint warehouse_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint warehouse_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index warehouse_label    on warehouse(label);
    create index warehouse_priority on warehouse(priority);
    create index warehouse_lastmod  on warehouse(lastmod desc);
    create index warehouse_state    on warehouse(state);
    create index warehouse_uid_acl  on warehouse(uid, acl);

-- drop table if exists cell;
    create sequence cell_seq;
    create table cell(
        id          int primary key default nextval('cell_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        warehouse   int not null,
        parent      int,
        depth       int not null default -1,
        
        usage       CellUsage not null default 'INTERNAL',
        vip         int,
        vip_org     int,
        dx          int not null default 0,         -- mm
        dy          int not null default 0,         -- mm
        dz          int not null default 0,         -- mm
        
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
        
        constraint cell_uk_code         unique(code),
        constraint cell_fk_warehouse    foreign key(warehouse)
            references warehouse(id)    on update cascade on delete cascade,
        constraint cell_fk_parent       foreign key(parent)
            references cell(id)         on update cascade on delete cascade,
        constraint cell_fk_uid          foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint cell_fk_gid          foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index cell_label         on cell(label);
    create index cell_priority      on cell(priority);
    create index cell_lastmod       on cell(lastmod desc);
    create index cell_state         on cell(state);
    create index cell_uid_acl       on cell(uid, acl);
    
    create or replace view v_cell as
        select w.label "warehouse_label", p.label "parent_label", cell.*
        from cell
            left join warehouse w on cell.warehouse=w.id
            left join cell p on cell.parent=p.id;

-- drop table if exists artcat;
    create sequence artcat_seq;
    create table artcat(
        id          int primary key default nextval('artcat_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        parent      int,
        depth       int not null default -1,
        skufmt      varchar(100),
        barfmt      varchar(100),
        count       int not null default 0, -- redundant.
        
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
        
        constraint artcat_uk_code   unique(code),
        constraint artcat_fk_parent foreign key(parent)
            references artcat(id)   on update cascade on delete cascade,
        constraint artcat_fk_uid    foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint artcat_fk_gid    foreign key(gid)
            references "group"(id)  on update cascade on delete set null
    );

    create index artcat_label          on artcat(label);
    create index artcat_priority       on artcat(priority);
    create index artcat_lastmod        on artcat(lastmod desc);
    create index artcat_state          on artcat(state);
    create index artcat_uid_acl        on artcat(uid, acl);

-- drop table if exists uom;
    create sequence uom_seq start with 1000;
    create table uom(
        id          int primary key default nextval('uom_seq'),
        code        varchar(20) not null,
        label       varchar(80) not null,
        description varchar(200),
        property    varchar(20) not null,
        parent      varchar(20),
        scale       double precision not null default 0,
        
        constraint uom_uk_code      unique(code),
        constraint uom_fk_parent    foreign key(parent)
            references uom(code)    on update cascade on delete cascade
    );

    create index uom_label          on uom(label);

    insert into uom(id, code, label, property) values(100, 'm', '米', '长度');
    insert into uom(id, code, label, property, parent, scale) values(101, 'mm', '毫米', '长度', 'm', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(102, 'cm', '厘米', '长度', 'm', 0.01);
    insert into uom(id, code, label, property, parent, scale) values(103, 'dm', '分米', '长度', 'm', 0.1);
    insert into uom(id, code, label, property, parent, scale) values(104, 'km', '千米', '长度', 'm', 1000);
    insert into uom(id, code, label, property, parent, scale) values(105, 'inch', '英寸', '长度', 'm', 0.0254);
    insert into uom(id, code, label, property, parent, scale) values(106, 'foot', '英尺', '长度', 'm', 0.3048);
    insert into uom(id, code, label, property, parent, scale) values(107, 'mile', '英里', '长度', 'm', 1609.34);
    insert into uom(id, code, label, property) values(200, 'm2', '平方米', '面积');
    insert into uom(id, code, label, property, parent, scale) values(201, 'mm2', '平方毫米', '面积', 'm2',  0.000001);
    insert into uom(id, code, label, property, parent, scale) values(202, 'cm2', '平方厘米', '面积', 'm2',  0.0001);
    insert into uom(id, code, label, property, parent, scale) values(203, 'dm2', '平方分米', '面积', 'm2',  0.01);
    insert into uom(id, code, label, property, parent, scale) values(204, 'km2', '平方千米', '面积', 'm2', 1000000);
    
    insert into uom(id, code, label, property) values(300, 'm3', '立方米', '体积');
    insert into uom(id, code, label, property, parent, scale) values(350, 'L', '升', '容量', 'm3', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(351, 'mL', '毫升', '容量', 'm3', 0.000001);
    
    insert into uom(id, code, label, property) values(400, 'g', '克', '质量');
    insert into uom(id, code, label, property, parent, scale) values(401, 'mg', '毫克', '质量', 'g', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(402, 'kg', '千克', '质量', 'g', 1000);
    insert into uom(id, code, label, property, parent, scale) values(403, 't', '吨', '质量', 'g', 1000000);
    insert into uom(id, code, label, property, parent, scale) values(404, 'lb', '磅', '质量', 'g', 453.59237);
    insert into uom(id, code, label, property, parent, scale) values(405, 'oz', '盎司', '质量', 'g', 28.3495231);
    insert into uom(id, code, label, property, parent, scale) values(406, 'ct', '克拉', '质量', 'g', 0.2);
    insert into uom(id, code, label, property) values(420, 'N', '牛顿', '力');
    insert into uom(id, code, label, property, parent, scale) values(421, 'kN', '千牛顿', '力', 'N', 1);
    
    insert into uom(id, code, label, property) values(900, 'pcs', '件', '数量');
    insert into uom(id, code, label, property, parent, scale) values(902, '张', '张', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(903, '只', '只', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(904, '支', '支', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(905, '套', '套', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(906, '个', '个', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(907, '台', '台', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(908, '瓶', '瓶', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(909, '桶', '桶', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(910, '箱', '箱', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(911, '粒', '粒', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(912, '条', '条', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(913, '盒', '盒', '数量', 'pcs', 1);
    
-- drop table if exists art;
    create sequence art_seq;
    create table art(
        id          int primary key default nextval('art_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        category    int,
        sku         varchar(30),
        barcode     varchar(30),
        uom         int not null default 900,       -- pcs
        uomprop     varchar(20) not null default '数量',
        digits      int not null default 2,
        
        spec        varchar(100),
        color       varchar(20),
        caution     varchar(100),
        
        dx          int not null default 0,         -- mm
        dy          int not null default 0,         -- mm
        dz          int not null default 0,         -- mm
        weight      double precision not null,      -- gram
        netweight   double precision not null,      -- gram

        supply      SupplyMethod not null default 'PRODUCE',
        supplydelay int not null default 0,         -- day
        bom         text,
        
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
        
        constraint art_uk_code      unique(code),
        constraint art_fk_category  foreign key(category)
            references artcat(id)   on update cascade on delete set null,
        constraint art_fk_uid       foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint art_fk_gid       foreign key(gid)
            references "group"(id)  on update cascade on delete set null
    );

    create index art_code           on art(code);
    create index art_label          on art(label);
    create index art_priority       on art(priority);
    create index art_lastmod        on art(lastmod desc);
    create index art_state          on art(state);
    create index art_uid_acl        on art(uid, acl);

-- drop table if exists artcell;
    create table artcell(
        art         int not null,
        cell        int not null,
        locked      boolean not null,
        description varchar(100),
        
        constraint artcell_uk       unique(art, cell),
        constraint artcell_fk_art   foreign key(art)
            references art(id)      on update cascade on delete cascade,
        constraint artcell_fk_cell  foreign key(cell)
            references cell(id)     on update cascade on delete cascade
    );

-- drop table if exists artwopt;
    create table artwopt(
        art         int not null,
        warehouse   int not null,
        reservation double precision not null default 0,
        checkperiod int not null default 365,
        checkexpire timestamp not null default now() + 365 * interval '86400',
        
        constraint artwopt_pk           primary key(art, warehouse),
        constraint artwopt_fk_art       foreign key(art)
            references art(id)          on update cascade on delete cascade,
        constraint artwopt_fk_warehouse foreign key(warehouse)
            references warehouse(id)    on update cascade on delete cascade
    );
    
-- drop table if exists topic;
    create sequence topic_seq;
    create table topic(
        id          int primary key default nextval('topic_seq'),
        op          int not null,
        subject     varchar(200) not null,
        text        text,
        votes       int not null default 0,
        
        cat         int,
        phase       int not null default 1,             -- 初步信息采集
        val         double precision not null default 0,
        -- valmax   double precision,
        
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
        
        t0          timestamptz,                -- deliver time
        t1          timestamptz,                -- deadline
        
        constraint topic_fk_uid     foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint topic_fk_gid     foreign key(gid)
            references "group"(id)  on update cascade on delete set null,
        constraint topic_fk_cat     foreign key(cat)
            references cat(id)      on update cascade on delete set null,
        constraint topic_fk_phase   foreign key(phase)
            references phase(id)    on update cascade on delete set null
    );

    create index topic_subject        on topic(subject);
    create index topic_priority       on topic(priority);
    create index topic_lastmod        on topic(lastmod desc);
    create index topic_state          on topic(state);
    create index topic_uid_acl        on topic(uid, acl);
    create index topic_t0t1           on topic(t0, t1);
    create index topic_t1             on topic(t1);

-- drop table if exists reply;
    create sequence reply_seq;
    create table reply(
        id          int primary key default nextval('reply_seq'),
        op          int not null,
        text        text not null,
        votes       int not null default 0,
        
        topic       int not null,
        parent      int,
        changes     text[],
        
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
        
        t0          timestamptz,                -- work begin time
        t1          timestamptz,                -- work end time
        
        constraint reply_fk_uid     foreign key(uid)
            references "user"(id)   on update cascade on delete set null,
        constraint reply_fk_gid     foreign key(gid)
            references "group"(id)  on update cascade on delete set null,
        constraint reply_fk_topic   foreign key(topic)
            references topic(id)    on update cascade on delete set null,
        constraint reply_fk_parent  foreign key(parent)
            references reply(id)    on update cascade on delete set null
    );

    create index reply_priority       on reply(priority);
    create index reply_lastmod        on reply(lastmod desc);
    create index reply_state          on reply(state);
    create index reply_uid_acl        on reply(uid, acl);
    create index reply_t0t1           on reply(t0, t1);
    create index reply_t1             on reply(t1);

-- drop table if exists topicparty;
    create sequence topicparty_seq;
    create table topicparty(
        id          int primary key default nextval('topicparty_seq'),
        topic       int not null,
        person      int,
        org         int,
        description varchar(60),
        
        constraint topicparty_fk_topic  foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint topicparty_fk_person foreign key(person)
            references person(id)       on update cascade on delete set null,
        constraint topicparty_fk_org    foreign key(org)
            references org(id)          on update cascade on delete set null
    );

-- drop table if exists topicatt;
    create sequence topicatt_seq;
    create table topicatt(
        id          int primary key default nextval('topicatt_seq'),
        topic       int not null,
        att         int not null,
        val         varchar(200),

        constraint topicatt_uk          unique(topic, att),
        constraint topicatt_fk_topic    foreign key(topic)
            references topic(id)        on update cascade on delete cascade,
        constraint topicatt_fk_att      foreign key(att)
            references att(id)          on update cascade on delete cascade
    );

-- drop table if exists topictag;
    create sequence topictag_seq;
    create table topictag(
        id          int primary key default nextval('topictag_seq'),
        topic       int not null,
        tag         int not null,

        constraint topictag_fk_topic    foreign key(topic)
            references topic(id)        on update cascade on delete cascade,
        constraint topictag_fk_tag      foreign key(tag)
            references tag(id)          on update cascade on delete cascade
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
            references reply(id)        on update cascade on delete set null,
        constraint replyparty_fk_person foreign key(person)
            references person(id)       on update cascade on delete set null,
        constraint replyparty_fk_org    foreign key(org)
            references org(id)          on update cascade on delete set null
    );

-- drop table if exists replyatt;
    create sequence replyatt_seq;
    create table replyatt(
        id          int primary key default nextval('replyatt_seq'),
        reply       int not null,
        att         int not null,
        val         varchar(200),

        constraint replyatt_uk          unique(reply, att),
        constraint replyatt_fk_reply    foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replyatt_fk_att      foreign key(att)
            references att(id)          on update cascade on delete cascade
    );

-- drop table if exists replytag;
    create sequence replytag_seq;
    create table replytag(
        id          int primary key default nextval('replytag_seq'),
        reply       int not null,
        tag         int not null,

        constraint replytag_fk_reply    foreign key(reply)
            references reply(id)        on update cascade on delete cascade,
        constraint replytag_fk_tag      foreign key(tag)
            references tag(id)          on update cascade on delete cascade
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

