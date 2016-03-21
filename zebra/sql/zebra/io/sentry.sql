--\import zebra.io.art
--\import zebra.io.sdoc

    create sequence sentry_seq start with 1000;
    create table sentry(
        id          bigint primary key default nextval('sentry_seq'),
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi

        doc         int not null
            references sdoc(id) on update cascade on delete cascade,

        art         int not null
            references art(id) on update cascade,

        --batch       varchar(30) not null default '',
        --divs        varchar(100),
        resale      boolean not null default false,
        olabel      varchar(30),    -- label override
        ospec       varchar(80),    -- spec override

        qty         numeric(20,2) not null,
        price       numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0,   -- cache
        comment     varchar(200),
        footnote    varchar(200)
    );

    -- trigger support
        create or replace function update_sdoc_stat() returns trigger as $$
        declare
            v record;
            c int;
            cqty real;
            ctotal real;
        begin
            for v in select count(*) "count", sum(qty) "qty", sum(total) "total"
                from sentry where doc=new.doc
            loop
                c := v."count";
                cqty := v.qty;
                ctotal := v.total;
            end loop;

            update sdoc set
                size = c, qty = cqty, total = ctotal
                where id = new.doc;
            return new;
        end $$ language plpgsql;

        create trigger sentry_insert
            after insert or delete on sentry
            for each row execute procedure update_sdoc_stat();

        create trigger sentry_update
            after update of qty, price on sentry
            for each row execute procedure update_sdoc_stat();
