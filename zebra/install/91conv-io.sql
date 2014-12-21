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
            case when parent is null then warehouse else parent+100 end,
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

    insert into stdoc(id, subject, text, cat, t0, t1, val, org, person,
            creation, lastmod, uid)
        select a.id, a.label "subject", a.description "text",
            cat.id "cat", begin_time "t0", end_time "t1", native_total "val", 
            case when p.stereo='ORG' then a.org else null end "org", 
            case when p.stereo='PER' then a.org else null end "person",
            a.created_date "creation", a.last_modified "lastmod", a.owner "uid"
        from old.stock_order a
            left join cat on a.subject=cat.code
            left join old.party p on a.org=p.id;

    insert into stentry(id, doc, art, place, price, qty, t0)
        select id,
            parent "doc", material "art", warehouse "place", 
            price, quantity, begin_time "t0"
        from old.stock_order_item;

-- order
    
    insert into saledoc
    
        
