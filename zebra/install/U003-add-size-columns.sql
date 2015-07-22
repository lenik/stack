alter table sdoc add column size int not null default 0;
alter table stdoc add column size int not null default 0;
alter table dldoc add column size int not null default 0;

update sdoc set size=(select count(*) from sentry where doc=sdoc.id);
update stdoc set size=(select count(*) from stentry where doc=stdoc.id);
update dldoc set size=(select count(*) from dlentry where doc=dldoc.id);


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

