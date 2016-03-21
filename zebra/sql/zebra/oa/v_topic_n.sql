--\import zebra.oa.topic
--\import zebra.io.sdoc

    create or replace view v_topic_n as select
        (select count(*) from topic) "total",
        (select count(distinct topic) from sdoc) "ordered";
