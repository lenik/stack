
    create or replace view v_fileinfo_dup as
        select a.*
        from fileinfo a
            left join
                (select dir, base, count(*) n from fileinfo
                    group by dir, base having(count(*) > 1)) c
                on a.dir=c.dir and a.base=c.base
        where c.dir is not null;

    create or replace view v_fileinfo_dupx as
        select a.*
        from v_fileinfo_dup a
            left join
                (select max(id) "maxid", dir, base
                    from v_fileinfo_dup group by dir, base) m
                on a.dir=m.dir and a.base=m.base
        where a.id < m.maxid;

    create or replace view v_fileinfo as
        select *,
            array(select tag || ':' || tag.label
                from filetag a left join tag on a.tag=tag.id where a.file=fileinfo.id) tags,
            array(select att || ':' || att.label || '=' || a.val
                from fileatt a left join att on a.att=att.id where a.file=fileinfo.id) atts
        from fileinfo;

    create or replace view v_filetags as
        select a.n, tag.*
        from (select tag, count(*) n from filetag group by tag) a
            left join tag on a.tag=tag.id
        order by priority, n desc;

    create or replace view v_filevotes as
        select a.n, f.*
        from (select file, sum(n) n from filevote group by file) a
            left join fileinfo f on a.file=f.id
        order by n desc;
