--\import zebra.io.art
--\import zebra.io.dldoc

    create sequence dlentry_seq start with 1000;
    create table dlentry(
        id          bigint primary key default nextval('dlentry_seq'),
--\mixin lily.mixin.Ex

        doc         int not null
            references dldoc(id) on update cascade on delete cascade,

        sentry      int,            -- XXX deprecated

        art         int
            references art(id) on update cascade on delete set null,

        qty         numeric(20,2) not null,
        price       numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0   -- cache
    );

    -- trigger support
        create or replace function update_dldoc_stat() returns trigger as $$
        declare
            v record;
            c int;
            cqty real;
            ctotal real;
        begin
            for v in select count(*) "count", sum(qty) "qty", sum(total) "total"
                from dlentry where doc=new.doc
            loop
                c := v."count";
                cqty := v.qty;
                ctotal := v.total;
            end loop;

            update dldoc set
                size = c, qty = cqty, total = ctotal
                where id = new.doc;
            return new;
        end $$ language plpgsql;

        create trigger dlentry_insert
            after insert or delete on dlentry
            for each row execute procedure update_dldoc_stat();

        create trigger dlentry_update
            after update of qty, price on dlentry
            for each row execute procedure update_dldoc_stat();
