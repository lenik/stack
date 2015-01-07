
-- integration

    create or replace view v_topic_n as select
        (select count(*) from topic) "total",
        (select count(distinct topic) from sdoc) "ordered";
    
    create or replace view v_sdoc_n as select
        (select count(*) from sdoc) "total",
        (select count(distinct sdoc) from dldoc) "delivered";

