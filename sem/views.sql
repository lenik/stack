create view file_blob_used as
    select * from file_blob
    where id in (select distinct file_blob from user_file);

create view file_blob_notused as
    select * from file_blob
    where id not in (select distinct file_blob from user_file);
