create or replace function show_empty_table()
    returns table(relname name) as $$
begin
    -- vacuum;
    return query
    select c.relname "relname" from pg_catalog.pg_class c where relpages = 0 
        and not relisshared and not relhassubclass and relkind = 'r' 
        and relnamespace <> (select oid from pg_namespace where nspname = 'pg_catalog');
end
$$ language plpgsql volatile;

