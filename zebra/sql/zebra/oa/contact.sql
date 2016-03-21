
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
        r4          varchar(30),
        address1    varchar(80),
        address2    varchar(80),
        postcode    varchar(8),

        tel         varchar(20),
        mobile      varchar(20),
        fax         varchar(20),
        email       varchar(30),
        web         varchar(80),
        qq          varchar(20),

        constraint contact_uk unique(org, ou, person, usage)
    );

    create index contact_region     on contact(region);
