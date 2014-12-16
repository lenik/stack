set constraints all deferred;

-- group, user, group_user

    insert into "group"(id, code, label, description, creation, lastmod, flags, state)
        select id, 
            name "code", coalesce(label, name), description,
            created_date "creation", last_modified "lastmod", ef "flags", state
        from old.principal where stereo = 'G';

    insert into "user"(id, gid0, code, label, description, creation, lastmod, flags, state)
        select id, 
            coalesce(group1, group1, 0) "gid0",
            name "code", coalesce(label, name), description,
            created_date "creation", last_modified "lastmod", ef "flags", state
        from old.principal where stereo = 'U';

    insert into group_user("group", "user")
        select "group", member "user" from old.group_member;

    select setval('user_seq', (select max(id) from "user"));
    
        insert into "user"(code, label)
            select keyword, label from old.party
            where id in (select distinct suggester from old.chance_action)
                and keyword not in (select code from "user");
    
-- contact

    insert into contact(_id, org, ou, person, address1, postcode, tel, mobile, fax, email, web, qq)
        select c.id "_id", 
            case when p.stereo='ORG' then c.party else null end "org",
            case when x.id is not null then x.id else null end "ou",
            case when p.stereo='PER' then c.party else null end "person",
            address "address1", post_code "postcode", tel, mobile, fax, email, website "web", qq
        from old.contact c
            left join old.party p on c.party=p.id
            left join old.org_unit x on c.id=x.contact;
    
-- org, orgunit, person, personrole

    insert into org(id, label, description, 
            contact, 
            birthday, size, taxid,
            customer, supplier, bank, bankacc, subject,
            creation, lastmod, flags, state, uid)
        select id, 
            label, memo "description",
            (select id from old.contact where party=party.id) "contact", 
            birthday, size, sid "taxid",
            customer, supplier, bank, bank_account "bankacc",
            interests "subject", 
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.party where stereo = 'ORG';

    insert into orgunit(id, label, description, 
            org, parent,
            creation, lastmod, flags, state, uid)
        select id, label, description,
            org, parent,
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.org_unit;

    insert into person(id, label, description, 
            contact,
            birthday, gender, homeland, ssn, employee, 
            customer, supplier, bank, bankacc, subject,
            creation, lastmod, flags, state, uid)
        select id, 
            label, memo "description",
            (select id from old.contact where party=party.id) "contact", 
            birthday, nullif(sex, 'x') "gender", census_register "homeland", sid "ssn", employee, 
            customer, supplier, bank, bank_account "bankacc",
            interests "subject", 
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.party where stereo = 'PER';

    insert into personrole(person, ou, org, role, description)
        select person, org_unit "ou", org, role,
            trim(role_detail || ' ' || description || ' ' || alt_org_unit)
        from old.person_role;

-- fileinfo
    -- convert user_file_tagname to tagv(5):
        --  mapping: user_file_tagname.id_new -> tag.id.
        insert into tag(tagv, label, description, priority)
            select 5, name, description, id from old.user_file_tagname;

        update old.user_file_tagname a set id_new=tag.id from tag where a.id=tag.priority;
        update tag set priority=0 where tagv=5;
    
    insert into fileinfo(id, label, description, path, size, sha1, op, org, person, val,
            --tags,
            t0, t1, creation, lastmod, uid)
        select
            a.id, a.label, a.description,
            d.path || '/' || a.name "path",
            b.length "size", b.id "sha1",
            a.operator "op",
            case when p.stereo='ORG' then a.party else null end "org", 
            case when p.stereo='PER' then a.party else null end "person",
            a.val,
            --array(select y.id_new from old.user_file_tags x
            --        left join old.user_file_tagname y on x.tag=y.id
            --    where user_file=a.id) tags,
            a.file_date "t0", a.expired_date "t1",
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.user_file a
            left join old.user_folder d on a.folder=d.id
            left join old.file_blob b on a.file_blob=b.id
            left join old.party p on a.party=p.id;
    
    insert into filetag(file, tag)
        select x.user_file "file", y.id_new "tag"
        from old.user_file_tags x
            left join old.user_file_tagname y on x.tag=y.id;

-- topic, reply
    
    insert into cat(schema, code, label)
        select 3, id, label from old.chance_category where id <> 'OTHE';
    insert into phase(schema, code, label)
        select 3, id, label from old.chance_stage;
    
    insert into topic(id, subject, text, cat, phase, priority, op, t0, t1,
            creation, lastmod, uid)
        select c.id, subject, content, cat.id "cat", phase.id "phase", coalesce(c.priority, 0),
            c.owner "op", anticipation_begin "t0", anticipation_end "t1",
            c.created_date "creation", c.last_modified "lastmod", c.owner "uid"
        from old.chance c
            left join cat on c.category = cat.code
            left join phase on c.stage = phase.code;

    insert into topicparty(topic, person, org, description)
        select c.chance "topic", 
            case when p.stereo='PER' then c.party else null end "person", 
            case when p.stereo='ORG' then c.party else null end "org", 
            c.role "description"
        from old.chance_party c left join old.party p on c.party = p.id;

    insert into topicatt(topic, att, val)               -- att 1: addr
        select id "topic", 1 "att", address "val"
            from old.chance where address is not null and address <> '';

    insert into topictag(topic, tag)
        select c.id, t.id from old.chance c, tag t
            where t.code = case c.source 
                when 'FRIE' then 'friend'
                when 'INDE' then 'indep'
                when 'INTRO' then 'client'
                when 'OLD' then 'resell'
                when 'PLAT' then 'inet'
                else null end;

    insert into topictag(topic, tag)
        select c.id, t.id from old.chance c, tag t
            where lower(c.procurement_method) = lower(t.code);

    insert into topictag(topic, tag)
        select c.id, t.id from old.chance c, tag t
            where t.code = case c.purchase_regulation 
                when 'Brnad' then 'PPYX'
                when 'Price' then 'JGYX'
                when 'Effective' then 'XJBYX'
                else 'OTHER' end;

    insert into reply(id, topic, op, t0, t1, text, creation, lastmod, uid)
        select id,
            case when chance is null then 1 else chance end "topic",
            actor "op", begin_time "t0", end_time "t1",
            more_info "text",
            created_date "creation", last_modified "lastmod", owner "uid"
        from old.chance_action;
    
    insert into replyparty(reply, person, org)
        select c.chance_action "reply", 
            case when p.stereo='PER' then c.parties else null end "person", 
            case when p.stereo='ORG' then c.parties else null end "org"
        from old.chance_action_parties c left join old.party p on c.parties = p.id;

    insert into replytag(reply, tag)
        select a.id "reply", tag.id "tag"
            from old.chance_action a, tag
            where chance is not null
                and case a.style when 'FACE' then 'f2f' else null end = tag.code;

    insert into replyatt(reply, att, val)               -- att 2: cost
        select id, 2, spending from old.chance_action
            where chance is not null
                and trim(spending) <> '';

    select setval('reply_seq', (select max(id) from reply));
    
        insert into reply(topic, op, text, changes, creation, lastmod, uid)
            select chance "topic", actor "op",
                '将 阶段 更改为 ' || phase.label,
                array['stage=' || phase.id],
                a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
            from old.chance_action a left join phase
                on a.stage = phase.code
            where chance is not null
                and a.stage != 'INIT' and a.stage != '';
        
        insert into reply(topic, parent, op, text, creation, lastmod, uid)
            select a.chance "topic", a.id "parent", u.id "op", 
                a.suggestion "text",
                a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
            from old.chance_action a
                left join old.party p on a.suggester = p.id
                left join "user" u on p.label = u.label
            where chance is not null
                and suggester is not null;

-- account, pay*, recv*
    
    insert into account(id, label)
        select id::int, label from old.account_subject;
    
    insert into acdoc(id, val, op, org, person, form, subject, text, year, t0, t1, creation, lastmod, uid)
        select a.id, a.value, 
            l."user" "op",
            case p.stereo when 'ORG' then p.id else null end "org",
            case p.stereo when 'PER' then p.id else null end "person",
            case a.stereo when 'PAY' then 81 when 'CRED' then 82 end "form",
            case a.stereo when 'PAY' then '付款单 - ' when 'CRED' then '收款单 - ' end
                || a.description "subject", 
            a.text,
            extract(year from a.begin_time) "year", a.begin_time "t0", a.begin_time "t1", 
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.fund_flow a
            left join old.party p on a.party=p.id
            left join old.party op on a.operator=op.id
            left join old.person_login l on op.id=l.person;

        select setval('acdoc_seq', (select max(id) from "acdoc"));
        
    -- create acdoc for tickets without fund_flow.
    alter table acdoc add column id_tmp int;
    insert into acdoc(id_tmp, subject, op, year, t0, t1, creation, lastmod, uid)
        select
            a.id "id_tmp",
            a.description "subject",
            l."user" "op",
            extract(year from a.begin_time) "year", a.begin_time "t0", a.end_time "t1",
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.account_ticket a
            left join old.fund_flow f on a.id=f.ticket
            left join old.person_login l on a.person=l.person
        where f.id is null;
        
    insert into acentry(doc, account, org, person, val)
        select 
            case when f.id is null then acdoc.id else f.id end "doc",
            a.subject::int "account",
            case p.stereo when 'ORG' then p.id else null end "org",
            case p.stereo when 'PER' then p.id else null end "person",
            (case debit_side when true then 1 else -1 end) * abs(a.value) "val"
        from old.account_ticket_item a
            left join old.account_ticket t on a.ticket=t.id
            left join acdoc on a.ticket=acdoc.id_tmp
            left join old.party p on a.party=p.id
            left join old.fund_flow f on a.ticket=f.ticket
        where a.stereo <> 'INIT';
    alter table acdoc drop column id_tmp;
    
    insert into acinit(year, account, org, person, val)
        select
            extract(year from a.begin_time) "year", 
            a.subject::int "account", 
            case p.stereo when 'ORG' then p.id else null end "org",
            case p.stereo when 'PER' then p.id else null end "person",
            a.value "val"
        from old.account_ticket_item a
            left join old.party p on a.party=p.id
        where a.stereo = 'INIT';

