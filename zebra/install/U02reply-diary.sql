 
    insert into diary(
            id, priority, creation, lastmod, flags, state, version,
            uid, gid, mode, acl,
            t0, t1, year,
            op, subject, text, cat, phase)
        select id,
            priority, creation, lastmod, flags, state, version,
            uid, gid, mode, acl,
            t0, t1, year,
            op,
            case when length(text)<=20 then text else substr(text, 1, 20)||'...' end "subject",
            case when length(text)<=20 then null else text end "text",
            cat, phase
        from reply
        where topic=1 or topic=1001;
    
    create sequence diaryparty_seq;
    create table diaryparty(
        id          int primary key default nextval('diaryparty_seq'),
        diary       int not null,
        person      int,
        org         int,
        description varchar(60),
        
        constraint diaryparty_fk_diary  foreign key(diary)
            references diary(id)            on update cascade on delete set null,
        constraint diaryparty_fk_person foreign key(person)
            references person(id)           on update cascade on delete set null,
        constraint diaryparty_fk_org    foreign key(org)
            references org(id)              on update cascade on delete set null
    );
    
    insert into diaryparty
        select * from replyparty
        where reply in (select id from reply where topic=1 or topic=1001);
    
    delete from replyparty where reply in (select id from reply where topic=1 or topic=1001);
    delete from reply where topic=1 or topic=1001;
    delete from topic where id=1 or id=1001;
    
    select setval('diary_seq',      greatest((select start_value from diary_seq),       (select max(id) from diary)));
    
