-- group, user

    create or replace view v_group as
        select *, 
            array(select "user" from group_user a where a."group"=g.id) users,
            array(select coalesce(label, code) from group_user a left join "user" u on a."user"=u.id 
                where a."group"=g.id) labels
        from "group" g;

    create or replace view v_user as
        select u.*, g.label "label0",
            array(select "group" from group_user a where a."user"=u.id) groups,
            array(select coalesce(label, code) from group_user a left join "group" g on a."group"=g.id
                where a."user"=u.id) labels
        from "user" u left join "group" g on u.gid0 = g.id;

-- contacts

    -- view: avct_contact
    select * from mkaview('public', 'contact', 'ct_');

    create or replace view v_contact as
        select org.label "org_label", 
            oo.label || ' - ' || ou.label "ou_label",
            p.label "person_label", c.*
        from contact c
            left join org           on c.org=org.id
            left join orgunit ou    on c.ou=ou.id
            left join org oo        on ou.org=oo.id
            left join person p      on c.person=p.id;

    create or replace view v_personrole as
        select o.label "org_label", ou.label "ou_label", p.label "person_label", r.*
        from personrole r
            left join person p on r.person=p.id
            left join orgunit ou on r.ou=ou.id
            left join org o on r.org=o.id;

-- files

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
    
-- topic, reply

    create or replace view v_topic as
        select *, 
            array(select tag || ':' || tag.label
                from topictag a left join tag on a.tag=tag.id where a.topic=topic.id) tags,
            array(select att || ':' || att.label || '=' || a.val
                from topicatt a left join att on a.att=att.id where a.topic=topic.id) atts
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
        
-- acdoc

    create or replace view v_acentry as
        select a.id, a.doc, d.subject,
            a.account, c.label "account_label",
            a.org, o.label "org_label",
            a.person, p.label "person_label",
            a.val, d.ndebit, d.ncredit, a.priority,
            d.subject "doc_subject", d.cat, d.phase,
            d.year, d.t0, d.t1, d.creation, d.lastmod
        from acentry a
            left join account c on a.account=c.id
            left join acdoc d on a.doc=d.id
            left join org o on a.org=o.id
            left join person p on a.person=p.id;

    create or replace view v_acdoc as
        select a.*, op.label "op_label", o.label "org_label", p.label "person_label",
            array(select e.account_label || '=' || e.val from v_acentry e where e.doc=a.id) "entries"
        from acdoc a
            left join org o on a.org=o.id
            left join person p on a.person=p.id
            left join "user" op on a.op=op.id;

    create or replace view v_acinits as
        select year, account, sum(val) from acinit group by year, account;
    
    create or replace view v_acinit as
        select a.id, a.year,
            a.account, c.label "account_label",
            a.org, o.label "org_label",
            a.person, p.label "person_label",
            a.val
        from acinit a
            left join account c on a.account=c.id
            left join org o on a.org=o.id
            left join person p on a.person=p.id;

-- art, place

    create or replace view v_artcat_n as select
        (select count(*) from artcat) total,
        (select count(distinct cat) from art) used;
        
    create or replace view v_artcat_hist as
        select a.*, c.* -- c.label c_label
        from (select cat, count(*) n, 
                max(lastmod) lastmod_max from art group by cat) a
            left join artcat c on a.cat=c.id
        order by priority, n desc;
    
    create or replace view v_place as
        select place.*, p.label "parent_label"
        from place
            left join place p on place.parent=p.id;

    create or replace view v_place_n as select
        (select count(*) from place) total,
        (select count(distinct place) from placeopt) used,
        (select count(distinct place) from placeopt where locked) locked;

-- sdoc, stdoc, dldoc

    create or replace view v_sdoc as
        select a.*, 
            prev.subject "prev_subject",
            form.label   "form_label",
            topic.subject "topic_subject",
            op.label     "op_label",
            org.label    "org_label",
            person.label "person_label",
            cat.label    "cat_label",
            phase.label  "phase_label"
        from sdoc a
            left join sdoc prev on a.prev=prev.id
            left join form on a.form=form.id
            left join topic on a.topic=topic.id
            left join "user" op on a.op=op.id
            left join org on a.org=org.id
            left join person on a.person=person.id
            left join cat on a.cat=cat.id
            left join phase on a.phase=phase.id;

    create or replace view v_sentry as
        select a.*,
            sdoc.subject "doc_subject",
            art.label "art_label",
            art.spec "art_spec"
        from sentry a
            left join sdoc on a.doc=sdoc.id
            left join art on a.art=art.id;

    create or replace view v_stinits as
        select year, art, sum(qty) from stinit group by year, art;
    
    create or replace view v_stinit as
        select a.id, a.year,
            a.art, r.label "art_label",
            a.place, p.label "place_label",
            a.batch, a.divs, a.qty
        from stinit a
            left join art r on a.art=r.id
            left join place p on a.place=p.id;

    create or replace view v_stdoc as
        select a.*, 
            prev.subject "prev_subject",
            form.label   "form_label",
            topic.subject "topic_subject",
            op.label     "op_label",
            org.label    "org_label",
            person.label "person_label",
            cat.label    "cat_label",
            phase.label  "phase_label"
        from stdoc a
            left join stdoc prev on a.prev=prev.id
            left join form on a.form=form.id
            left join topic on a.topic=topic.id
            left join "user" op on a.op=op.id
            left join org on a.org=org.id
            left join person on a.person=person.id
            left join cat on a.cat=cat.id
            left join phase on a.phase=phase.id;

    create or replace view v_stdoc_n as
        select
            (select count(*) from stdoc) "total",
            (select count(*) from stdoc where phase>1201) "running";

    create or replace view v_art_n as
        select
            (select count(*) from art) total,
            (select count(distinct art) from stentry) used;

    create or replace view v_stentry as
        select a.*,
            stdoc.subject "doc_subject",
            art.label "art_label",
            place.label "place_label"
        from stentry a
            left join stdoc on a.doc=stdoc.id
            left join art on a.art=art.id
            left join place on a.place=place.id;

    create or replace view v_dldoc as
        select a.*, 
            prev.subject "prev_subject",
            sdoc.subject "sdoc_subject",
            op.label     "op_label",
            org.label    "org_label",
            person.label "person_label",
            cat.label    "cat_label",
            phase.label  "phase_label"
        from dldoc a
            left join dldoc prev on a.prev=prev.id
            left join sdoc on a.sdoc=sdoc.id
            left join "user" op on a.op=op.id
            left join org on a.org=org.id
            left join person on a.person=person.id
            left join cat on a.cat=cat.id
            left join phase on a.phase=phase.id;

    create or replace view v_dldoc_n as select
        (select count(*) from dldoc) "total",
        (select count(*) from dldoc where t1 is null) "shipping";
        
    create or replace view v_dlentry as
        select a.*,
            dldoc.subject "doc_subject",
            art.label "art_label",
            art.spec "art_spec"
        from dlentry a
            left join dldoc on a.doc=dldoc.id
            left join art on a.art=art.id;

-- integration

    create or replace view v_topic_n as select
        (select count(*) from topic) "total",
        (select count(distinct topic) from sdoc) "ordered";
    
    create or replace view v_sdoc_n as select
        (select count(*) from sdoc) "total",
        (select count(distinct sdoc) from dldoc) "delivered";

    create or replace view v_log as
              select 'topic' c, id, form, op, subject "msg", t0, creation from topic
        union select 'reply' c, id, null "form", op, text, t0, creation from reply
        union select 'diary' c, id, form, op, subject, t0, creation from diary
        union select 'acdoc' c, id, form, op, subject, t0, creation from acdoc
        union select 'sdoc'  c, id, form, op, subject, t0, creation from sdoc
        union select 'stdoc' c, id, form, op, subject, t0, creation from stdoc
        union select 'dldoc' c, id, form, op, subject, t0, creation from dldoc;

