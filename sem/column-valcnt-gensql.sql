SELECT 
  concat('select count(distinct "', column_name, '") "CNT_', column_name, '" ',
    'from "', table_name, '"; ')
  FROM 
    information_schema.columns
    where table_schema='public';

