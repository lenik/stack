vacuum;

SELECT relname FROM pg_catalog.pg_class WHERE relpages = 0 
AND NOT relisshared AND NOT relhassubclass AND relkind = 'r' 
AND relnamespace <> 
    (SELECT oid FROM pg_namespace WHERE nspname = 'pg_catalog'); 

