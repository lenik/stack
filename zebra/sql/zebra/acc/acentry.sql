--\import lily.account
--\import zebra.oa.org
--\import zebra.oa.person

    create sequence acentry_seq start with 1000;
    create table acentry(
        id          bigint primary key default nextval('acentry_seq'),
--\mixin lily.mixin.Ex

        doc         bigint not null
            references acdoc(id) on update cascade on delete cascade,

        account     int not null
            references account(id) on update cascade,

        org         int
            references org(id) on update cascade on delete set null,

        person      int
            references person(id) on update cascade on delete set null,

        -- positive for the debit side, or negative for the credit side.
        val         numeric(20,2) not null default 0
    );
