-- Clean duplicated contacts, keep the latest ones.
    delete from old.contact c
        where party in (select party from old.contact where party is not null group by party having count(*)>1)
          and last_modified < (select max(last_modified) from old.contact t where t.party=c.party);

    delete from old.contact c
        where party in (select party from old.contact where party is not null group by party having count(*)>1)
          and created_date < (select max(created_date) from old.contact t where t.party=c.party);

    -- If tel/mobile/fax is too long, move them to description.
    update old.contact set description = description || tel, tel = null where length(tel)>20;
    update old.contact set description = description || mobile, mobile = null where length(mobile)>20;
    update old.contact set description = description || fax, fax = null where length(fax)>20;

-- Move full_name -> label
    update old.party set label=full_name, full_name=null 
        where full_name is not null and full_name <> '';

-- 
    update old.person_role set role=role_detail, role_detail=null
        where role='' or role is null;

-- user_file: guess the value from description
    alter table old.user_file add column val float;
    
    update old.user_file set val=description::float, description=null
        where description ~ '^([0-9]+\.?[0-9]*)$';
    
    update old.user_file set
        val=regexp_replace(description, 
            '^(.*?)(,?工?程?合?同?暂?定?结?算?票?面?协?议?总?金?[价计额]为?)([0-9.]+)(.*)$', '\3')::float,
        description=regexp_replace(description,
            '^(.*?)(,?工?程?合?同?暂?定?结?算?票?面?协?议?总?金?[价计额]为?)([0-9.]+)(.*)$', '\1\4')
        where description ~ '^(.*?)(,?工?程?合?同?暂?定?结?算?票?面?协?议?总?金?[价计额]为?)([0-9.]+)(.*)$';

-- user_file: fix depth

    update old.user_folder a set path=name where depth=1;
    update old.user_folder a set path=p.path || '/' || a.name
        from old.user_folder p where a.depth=2 and a.parent=p.id;
    update old.user_folder a set path=p.path || '/' || a.name
        from old.user_folder p where a.depth=3 and a.parent=p.id;
    update old.user_folder a set path=p.path || '/' || a.name
        from old.user_folder p where a.depth=4 and a.parent=p.id;
    update old.user_folder a set path=p.path || '/' || a.name
        from old.user_folder p where a.depth=5 and a.parent=p.id;
    update old.user_folder a set path=p.path || '/' || a.name
        from old.user_folder p where a.depth=6 and a.parent=p.id;
    
    update old.user_file set folder=18 where folder is null; -- 内部文档

-- user_file_tagname: +id_new
    alter table old.user_file_tagname add column id_new int;


-- fill account_ticket.description from any item and fund_flow record.
    alter table old.account_ticket add column person int;
    update old.account_ticket t set description=i.description, person=i.person
        from old.account_ticket_item i where t.id=i.ticket;
    update old.account_ticket t set description=f.description
        from old.fund_flow f where t.id=f.ticket;

