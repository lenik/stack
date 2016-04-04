--\import zebra.oa.topic

    create or replace view v_topic as
        select *,
            array(select tag || ':' || tag.label
                from topictag a left join tag on a.tag=tag.id where a.topic=topic.id) tags,
            array(select parm || ':' || parm.label || '=' || a.val
                from topic_parm a left join parm on a.parm=parm.id where a.topic=topic.id) parms
        from topic;

    create or replace view v_topictags as
        select a.n, tag.*
        from (select tag, count(*) n from topictag group by tag) a
            left join tag on a.tag=tag.id
        order by priority, n desc;

    create or replace view v_topicvotes as
        select a.n, f.*
        from (select topic, sum(n) n from topicvote group by topic) a
            left join topic f on a.topic=f.id
        order by n desc;
