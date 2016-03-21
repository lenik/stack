--\import zebra.oa.reply

    create or replace view v_reply as
        select *,
            array(select tag || ':' || tag.label
                from replytag a left join tag on a.tag=tag.id where a.reply=reply.id) tags,
            array(select att || ':' || att.label || '=' || a.val
                from replyatt a left join att on a.att=att.id where a.reply=reply.id) atts
        from reply;

    create or replace view v_replytags as
        select a.n, tag.*
        from (select tag, count(*) n from replytag group by tag) a
            left join tag on a.tag=tag.id
        order by priority, n desc;

    create or replace view v_replyvotes as
        select a.n, f.*
        from (select reply, sum(n) n from replyvote group by reply) a
            left join reply f on a.reply=f.id
        order by n desc;
