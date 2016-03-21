--\import zebra.io.art
--\import zebra.io.place
--\import zebra.io.stdoc

    create sequence stentry_seq start with 1000;
    create table stentry(
        id          bigint primary key default nextval('stentry_seq'),
--\mixin lily.mixin.Ex
--\mixin lily.mixin.Mi

        doc         int not null
            references stdoc(id) on update cascade on delete cascade,

        art         int not null
            references art(id) on update cascade on delete set null,

        place       int not null
            references place(id) on update cascade on delete set null,

        batch       varchar(30) not null default '',
        divs        varchar(100),

        qty         numeric(20,2) not null,
        price       numeric(20,2) not null default 0,
        total       numeric(20,2) not null default 0,   -- cache
        comment     varchar(200)
    );

    -- trigger support
        create or replace function update_stdoc_stat() returns trigger as $$
        declare
            v record;
            c int;
            cqty real;
            ctotal real;
        begin
            for v in select count(*) "count", sum(qty) "qty", sum(total) "total"
                from stentry where doc=new.doc
            loop
                c := v."count";
                cqty := v.qty;
                ctotal := v.total;
            end loop;

            update stdoc set
                size = c, qty = cqty, total = ctotal
                where id = new.doc;
            return new;
        end $$ language plpgsql;

        create trigger stentry_insert
            after insert or delete on stentry
            for each row execute procedure update_stdoc_stat();

        create trigger stentry_update
            after update of qty, price on stentry
            for each row execute procedure update_stdoc_stat();
