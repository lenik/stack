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
        
        category    int,
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

