--\import zebra.oa.org
--\import zebra.oa.person
--\import zebra.io.art

    create type PlaceUsage as enum(
        'GROUP', 'INTERNAL', 'SUPPLIER', 'CUSTOMER',
        'SUBQUALITY', 'WASTE');

    create sequence place_seq start with 1000;
    create table place(
        id          int primary key default nextval('place_seq'),
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        parent      int
            references place(id) on update cascade on delete cascade,

        depth       int not null default -1,

        usage       PlaceUsage not null default 'INTERNAL',

        vip         int
            references person(id) on update cascade on delete set null,

        vip_org     int
            references org(id) on update cascade on delete set null,

        dx          int not null default 0,         -- mm
        dy          int not null default 0,         -- mm
        dz          int not null default 0          -- mm
    );

    create index place_label         on place(label);
    create index place_lastmod       on place(lastmod desc);
    create index place_priority      on place(priority);
    create index place_state         on place(state);
    create index place_uid_acl       on place(uid, acl);

-- drop table if exists placeopt;
    create table placeopt(
        art         int not null
            references art(id) on update cascade on delete cascade,

        place       int not null
            references place(id) on update cascade on delete cascade,

        locked      boolean not null,
        reservation numeric(20, 2) not null default 0,
        checkperiod int not null default 365,
        checkexpire timestamp not null default now() + 365 * interval '86400',
        description varchar(100),

        constraint placeopt_uk unique(art, place)
    );
