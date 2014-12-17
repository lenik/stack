-- ENUMs

    create type PlaceUsage as enum(
        'GROUP', 'INTERNAL', 'SUPPLIER', 'CUSTOMER',
        'SUBQUALITY', 'WASTE');
    
    create type SupplyMethod as enum(
        'BUY', 'PRODUCE');

-- drop table if exists place;
    create sequence place_seq;
    create table place(
        id          int primary key default nextval('place_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        contact     int,
        
        parent      int,
        depth       int not null default -1,
        
        usage       PlaceUsage not null default 'INTERNAL',
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
        
        constraint place_uk_code     unique(code),
        constraint place_fk_parent   foreign key(parent)
            references place(id)         on update cascade on delete cascade,
        constraint place_fk_uid      foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint place_fk_gid      foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index place_label         on place(label);
    create index place_lastmod       on place(lastmod desc);
    create index place_priority      on place(priority);
    create index place_state         on place(state);
    create index place_uid_acl       on place(uid, acl);
    
    create or replace view v_place as
        select place.*, p.label "parent_label"
        from place
            left join place p on place.parent=p.id;

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
            references artcat(id)       on update cascade on delete cascade,
        constraint artcat_fk_uid    foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint artcat_fk_gid    foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index artcat_label       on artcat(label);
    create index artcat_lastmod     on artcat(lastmod desc);
    create index artcat_priority    on artcat(priority);
    create index artcat_state       on artcat(state);
    create index artcat_uid_acl     on artcat(uid, acl);

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
            references uom(code)        on update cascade on delete cascade
    );

    create index uom_label          on uom(label);

    insert into uom(id, code, label, property) values(1, 'pcs', '件', '数量');
    
    insert into uom(id, code, label, property) values(100, 'm', '米', '长度');
    insert into uom(id, code, label, property, parent, scale) values(101, 'mm',     '毫米',   '长度', 'm', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(102, 'cm',     '厘米',   '长度', 'm', 0.01);
    insert into uom(id, code, label, property, parent, scale) values(103, 'dm',     '分米',   '长度', 'm', 0.1);
    insert into uom(id, code, label, property, parent, scale) values(104, 'km',     '千米',   '长度', 'm', 1000);
    insert into uom(id, code, label, property, parent, scale) values(105, 'inch',   '英寸',   '长度', 'm', 0.0254);
    insert into uom(id, code, label, property, parent, scale) values(106, 'foot',   '英尺',   '长度', 'm', 0.3048);
    insert into uom(id, code, label, property, parent, scale) values(107, 'mile',   '英里',   '长度', 'm', 1609.34);
    insert into uom(id, code, label, property) values(200, 'm2', '平方米', '面积');
    insert into uom(id, code, label, property, parent, scale) values(201, 'mm2',    '平方毫米', '面积', 'm2',  0.000001);
    insert into uom(id, code, label, property, parent, scale) values(202, 'cm2',    '平方厘米', '面积', 'm2',  0.0001);
    insert into uom(id, code, label, property, parent, scale) values(203, 'dm2',    '平方分米', '面积', 'm2',  0.01);
    insert into uom(id, code, label, property, parent, scale) values(204, 'km2',    '平方千米', '面积', 'm2', 1000000);
    
    insert into uom(id, code, label, property) values(300, 'm3', '立方米', '体积');
    insert into uom(id, code, label, property, parent, scale) values(350, 'L',      '升',    '容量', 'm3', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(351, 'mL',     '毫升',   '容量', 'm3', 0.000001);
    
    insert into uom(id, code, label, property) values(400, 'g', '克', '质量');
    insert into uom(id, code, label, property, parent, scale) values(401, 'mg',     '毫克',   '质量', 'g', 0.001);
    insert into uom(id, code, label, property, parent, scale) values(402, 'kg',     '千克',   '质量', 'g', 1000);
    insert into uom(id, code, label, property, parent, scale) values(403, 't',      '吨',    '质量', 'g', 1000000);
    insert into uom(id, code, label, property, parent, scale) values(410, 'lb',     '磅',    '质量', 'g', 453.59237);
    insert into uom(id, code, label, property, parent, scale) values(411, 'oz',     '盎司',   '质量', 'g', 28.3495231);
    insert into uom(id, code, label, property, parent, scale) values(420, '斤',      '斤',    '质量', 'g', 500);
    insert into uom(id, code, label, property, parent, scale) values(421, '两',      '两',    '质量', 'g', 50);
    insert into uom(id, code, label, property, parent, scale) values(422, '钱',      '钱',    '质量', 'g', 5);
    insert into uom(id, code, label, property, parent, scale) values(430, 'ct',     '克拉',   '质量', 'g', 0.2);
    insert into uom(id, code, label, property) values(480, 'N', '牛顿', '力');
    insert into uom(id, code, label, property, parent, scale) values(481, 'kN',     '千牛顿',  '力', 'N', 1);
    
    /*
    insert into uom(id, code, label, property, parent, scale) values(901, '个', '个', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(902, '张', '张', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(903, '只', '只', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(904, '支', '支', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(905, '套', '套', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(907, '台', '台', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(908, '瓶', '瓶', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(909, '桶', '桶', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(910, '箱', '箱', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(911, '粒', '粒', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(912, '条', '条', '数量', 'pcs', 1);
    insert into uom(id, code, label, property, parent, scale) values(913, '盒', '盒', '数量', 'pcs', 1);
    */
    
-- drop table if exists art;
    create sequence art_seq;
    create table art(
        id          int primary key default nextval('art_seq'),
        code        varchar(20),
        label       varchar(80),
        description varchar(200),
        
        cat         int,
        sku         varchar(30),
        barcode     varchar(30),
        uom         int not null default 1,         -- pcs
        uomalt      varchar(10),
        uomprop     varchar(20),
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
        constraint art_fk_cat       foreign key(cat)
            references artcat(id)       on update cascade on delete set null,
        constraint art_fk_uid       foreign key(uid)
            references "user"(id)       on update cascade on delete set null,
        constraint art_fk_gid       foreign key(gid)
            references "group"(id)      on update cascade on delete set null
    );

    create index art_code           on art(code);
    create index art_label          on art(label);
    create index art_lastmod        on art(lastmod desc);
    create index art_priority       on art(priority);
    create index art_state          on art(state);
    create index art_uid_acl        on art(uid, acl);

-- drop table if exists placeopt;
    create table placeopt(
        art         int not null,
        place       int not null,
        locked      boolean not null,
        reservation numeric(20, 2) not null default 0,
        checkperiod int not null default 365,
        checkexpire timestamp not null default now() + 365 * interval '86400',
        description varchar(100),
        
        constraint placeopt_uk       unique(art, place),
        constraint placeopt_fk_art   foreign key(art)
            references art(id)          on update cascade on delete cascade,
        constraint placeopt_fk_place foreign key(place)
            references place(id)         on update cascade on delete cascade
    );

-- drop table if exists stock;
    create sequence stdoc_seq start with 100;
    create table stdoc(
        id          bigint primary key default nextval('stdoc_seq'),
        prev        bigint,         -- previous doc
        form        int,
        subject     varchar(200) not null,
        text        text,
        args        text,           -- used with the form.
        
        topic       int,
        op          int,
        org         int,
        ou          int,
        person      int,
        
        cat         int not null,   -- aka. stock order subject
        phase       int,            -- aka. stock stage
        val         double precision not null default 0,
        
        year        int not null default 0, -- same year of t0.
        t0          date,           -- begin date
        t1          date,           -- end date
        
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
        
        constraint stdoc_fk_cat     foreign key(cat)
            references cat(id)          on update cascade on delete set null,
        constraint stdoc_fk_form    foreign key(form)
            references form(id)         on update cascade on delete set null,
        constraint stdoc_fk_gid     foreign key(gid)
            references "group"(id)      on update cascade on delete set null,
        constraint stdoc_fk_op      foreign key(op)
            references "user"(id)       on update cascade on delete set null,
        constraint stdoc_fk_org     foreign key(org)
            references org(id)          on update cascade,
        constraint stdoc_fk_person  foreign key(person)
            references person(id)       on update cascade,
        constraint stdoc_fk_phase   foreign key(phase)
            references phase(id)        on update cascade on delete set null,
        constraint stdoc_fk_prev    foreign key(prev)
            references stdoc(id)        on update cascade on delete set null,
        constraint stdoc_fk_topic   foreign key(topic)
            references topic(id)        on update cascade on delete set null,
        constraint stdoc_fk_uid     foreign key(uid)
            references "user"(id)       on update cascade on delete set null
    );

    create index stdoc_lastmod         on stdoc(lastmod desc);
    create index stdoc_priority        on stdoc(priority);
    create index stdoc_state           on stdoc(state);
    create index stdoc_subject         on stdoc(subject);
    create index stdoc_uid_acl         on stdoc(uid, acl);
    create index stdoc_year            on stdoc(year);
    create index stdoc_t0t1            on stdoc(t0, t1);
    create index stdoc_t1              on stdoc(t1);

-- drop table if exists stentry;
    create sequence stentry_seq start with 1000;
    create table stentry(
        id          bigint primary key default nextval('stentry_seq'),
        doc         bigint not null,
        
        art         int not null,
        place       int not null,
        batch       varchar(30) not null default '',
        divs        varchar(100),
        
        qty         numeric(20,2) not null,
        price       numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0,   -- cache
        description varchar(200),
        
        priority    int not null default 0,
        flags       int not null default 0,
        state       int not null default 0,
        
        constraint stentry_fk_art   foreign key(art)
            references art(id)          on update cascade on delete set null,
        constraint stentry_fk_place foreign key(place)
            references place(id)        on update cascade on delete set null,
        constraint stentry_fk_doc   foreign key(doc)
            references stdoc(id)        on update cascade on delete cascade
    );

-- drop table if exists stinit;
    create sequence stinit_seq start with 1000;
    create table stinit(
        id          int primary key default nextval('stinit_seq'),
        year        int not null,
        art         int not null,
        place       int not null,
        batch       varchar(30) not null default '',
        divs        varchar(100),
        qty         numeric(20,2) not null,
        
        constraint stinit_uk_code   unique(year, art, place, batch, divs),
        constraint stinit_fk_art    foreign key(art)
            references art(id)          on update cascade on delete set null,
        constraint stinit_fk_place  foreign key(place)
            references place(id)        on update cascade on delete set null
    );
    
    create view v_stinits as
        select year, art, sum(qty) from stinit group by year, art;
    
    create view v_stinit as
        select a.id, a.year,
            a.art, r.label "art_label",
            a.place, p.label "place_label",
            a.batch, a.divs, a.qty
        from stinit a
            left join art r on a.art=r.id
            left join place p on a.place=p.id;

    create or replace view v_place_n as select
        (select count(*) from place) total,
        (select count(distinct place) from placeopt) used,
        (select count(distinct place) from placeopt where locked) locked;

    create or replace view v_artcat_n as select
        (select count(*) from artcat) total,
        (select count(distinct cat) from art) used;
        
    create or replace view v_artcat_hist as
        select a.*, c.* -- c.label c_label
        from (select cat, count(*) n, 
                max(lastmod) lastmod_max from art group by cat) a
            left join artcat c on a.cat=c.id
        order by priority, n desc;
    
    create or replace view v_art_n as
        select
            (select count(*) from art) total,
            (select count(distinct art) from stentry) used;

