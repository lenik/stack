--\import lily.account
--\import zebra.oa.org
--\import zebra.oa.person

    create sequence acinit_seq start with 1000;
    create table acinit(
        id          int primary key default nextval('acinit_seq'),
        year        int not null,

        account     int not null
            references account(id) on update cascade,

        org         int
            references org(id) on update cascade on delete set null,

        person      int
            references person(id) on update cascade on delete set null,

        val         numeric(20,2) not null default 0,

        constraint acinit_uk unique(year, account, org, person),
    );
