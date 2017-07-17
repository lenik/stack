--\import lily.account
--\import lily.util.uom
--\import zebra.io.artcat

    create type SupplyMethod as enum(
        'BUY', 'PRODUCE');

    create sequence art_seq start with 1000;
    create table art(
        id          int primary key default nextval('art_seq'),
        code        varchar(30) unique,
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        cat         int
            references artcat(id) on update cascade on delete set null,

        sku         varchar(30),
        barcode     varchar(30),
        uom         int
            references uom(id) on update cascade,
        uomprop     varchar(20),
        digits      int not null default 2,

        spec        varchar(100),
        color       varchar(20),
        caution     varchar(100),

        dx          int not null default 0,     -- mm
        dy          int not null default 0,     -- mm
        dz          int not null default 0,     -- mm
        weight      float not null default 0,   -- gram
        netweight   float not null default 0,   -- gram

        supply      SupplyMethod not null default 'PRODUCE',
        supplydelay int not null default 0,     -- day
        bom         jsonb
    );

    create index art_label          on art(label);
    create index art_lastmod        on art(lastmod desc);
    create index art_priority       on art(priority);
    create index art_state          on art(state);
    create index art_uid_acl        on art(uid, acl);

    insert into art(id, label, description)
        values(0, '未指定', '警告：该数据项仅用于数据升级，请重新设定。');
