--\import zebra.io.sdoc
--\import zebra.io.dldoc

    create or replace view v_sdoc_n as select
        (select count(*) from sdoc) "total",
        (select count(distinct sdoc) from dldoc) "delivered";
