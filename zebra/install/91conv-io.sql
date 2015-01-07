-- place, art
    
    insert into place(id, label, description, depth,
            creation, lastmod, flags, state, uid)
        select id, label, description, 0,
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.stock_warehouse;

    insert into place(id, label, description, 
            parent, depth, usage,
            creation, lastmod, flags, state, uid)
        select id+100, label, description,
            coalesce(parent+100, warehouse) "parent",
            depth, 'INTERNAL',   -- old depth based on 1
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.stock_location;

    insert into artcat(id, label, description, 
            parent, depth,
            creation, lastmod, flags, state, uid)
        select id, label, description,
            parent, depth,
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.material_category;

    insert into art(id, label, description, 
            cat, barcode, uom, uomalt, 
            spec, weight, netweight,
            creation, lastmod, flags, state, uid)
        select m.id, m.label, m.description,
            category, bar_code, coalesce(u.id, 1) "uom", 
                case when u.id is null then m.unit else null end "uomalt",
            model_spec, package_weight, net_weight,
            m.created_date "creation", m.last_modified "lastmod", m.ef "flags", m.state, m.owner "uid"
        from old.material m
            left join uom u on m.unit = u.code;

-- stocking

    insert into stdoc(id, subject, text, cat, t0, t1, total, org, person,
            creation, lastmod, uid)
        select a.id, a.label "subject", a.description "text",
            cat.id "cat",
            coalesce(a.begin_time, a.created_date) "t0",
            a.end_time "t1",
            coalesce(native_total, 0) "total", 
            case when p.stereo='ORG' then a.org else null end "org", 
            case when p.stereo='PER' then a.org else null end "person",
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.stock_order a
            left join cat on a.subject=cat.code
            left join old.party p on a.org=p.id;

    insert into stentry(id, doc, art, place, price, qty, t0)
        select id,
            parent "doc", material "art", warehouse "place", 
            price, quantity,
            begin_time "t0"
        from old.stock_order_item;

-- sales order
    
    insert into sdoc(id, subject, text, t0, 
            org, person, total, topic, op, state,
            creation, lastmod, uid)
        select a.id,
            a.label, a.description,
            coalesce(a.begin_time, a.created_date) "t0",
            case when c.stereo='ORG' then a.customer else null end "org", 
            case when c.stereo='PER' then a.customer else null end "person",
            coalesce(a.native_total, 0) "total",
            a.chance "topic",
            l."user" "op",
            case when a.valid then 0 else 417 end "state",
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.make_order a
            left join old.party c on a.customer=c.id
            left join old.party s on a.salesman=s.id
            left join old.person_login l on s.id=l.person;
        
    insert into sentry(id,
            doc, art, qty, price, total, t1, comment,
            resale, olabel, ospec)
        select id,
            parent "doc",
            coalesce(material, 0) "art",
            quantity "qty", price, quantity*price "total",
            deadline,
            description "comment",
            nameplate "resale",
            external_product_name "olabel",
            external_model_spec "ospec"
        from old.make_order_item a;

    insert into dldoc(id, subject, text,
            sdoc, org, person, total, t0)
        select a.id,
            a.label "subject", a.description "text",
            "order" "sdoc", 
            case when c.stereo='ORG' then a.customer else null end "org", 
            case when c.stereo='PER' then a.customer else null end "person",
            coalesce(a.native_total, 0) "total",
            a.begin_time "t0"
        from old.delivery_note a
            left join old.party c on a.customer=c.id;

    insert into dlentry(doc, sentry, art, qty, price, total)
        select
            a.parent "doc",
            a.order_item "sentry",
            coalesce(a.material, x.material, 0) "art",
            a.quantity "qty",
            a.price,
            a.native_total "total"
        from old.delivery_note_item a
            left join old.make_order_item x on a.order_item=x.id;
        
