--\import zebra.oa.reply

    create or replace view v_reply as
        select *,
            array(select tag || ':' || tag.label
                from replytag a left join _tag tag on a.tag=tag.id where a.reply=reply.id) tags,
            array(select parm || ':' || parm.label || '=' || a.val
                from reply_parm a left join _parm parm on a.parm=parm.id where a.reply=reply.id) parms
        from reply;

    create or replace view v_replytags as
        select a.n, tag.*
        from (select tag, count(*) n from replytag group by tag) a
            left join _tag tag on a.tag=tag.id
        order by priority, n desc;

    create or replace view v_replyvotes as
        select a.n, f.*
        from (select reply, sum(n) n from replyvote group by reply) a
            left join reply f on a.reply=f.id
        order by n desc;
