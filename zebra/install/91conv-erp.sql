-- warehouse, product
    
    insert into warehouse(id, label, description, 
            creation, lastmod, flags, state, uid)
        select id, label, description,
            created_date "creation", last_modified "lastmod", ef "flags", state, owner "uid"
        from old.stock_warehouse;

    insert into cell(id, label, description, 
            warehouse, parent, depth, usage,
            creation, lastmod, flags, state, uid)
        select id, label, description,
            warehouse, parent, depth, 'INTERNAL',
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
            category, barcode, uom, uomalt, 
            spec, weight, netweight,
            creation, lastmod, flags, state, uid)
        select m.id, m.label, m.description,
            category, bar_code, coalesce(u.id, 1) "uom", 
                case when u.id is null then m.unit else null end "uomalt",
            model_spec, package_weight, net_weight,
            m.created_date "creation", m.last_modified "lastmod", m.ef "flags", m.state, m.owner "uid"
        from old.material m
            left join uom u on m.unit = u.code;

