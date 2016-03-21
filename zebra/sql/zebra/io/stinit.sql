--\import zebra.io.art
--\import zebra.io.place

    create sequence stinit_seq start with 1000;
    create table stinit(
        id          int primary key default nextval('stinit_seq'),
        year        int not null,

        art         int not null
            references art(id) on update cascade on delete set null,

        place       int not null
            references place(id) on update cascade on delete set null,

        batch       varchar(30) not null default '',
        divs        varchar(100),
        qty         numeric(20,2) not null,

        constraint stinit_uk unique(year, art, place, batch, divs)
    );
