-- year
    update topic set year=coalesce(extract(year from t0), extract(year from creation));
    update reply set year=coalesce(extract(year from t0), extract(year from creation));
    update acdoc set year=coalesce(extract(year from t0), extract(year from creation));
    update sdoc  set year=coalesce(extract(year from t0), extract(year from creation));
    update stdoc set year=coalesce(extract(year from t0), extract(year from creation));
    update dldoc set year=coalesce(extract(year from t0), extract(year from creation));


    update sdoc set qty = y.qty_sum, total = y.total
        from (select doc, sum(qty) qty_sum, sum(qty*price) total from sentry group by doc) y
        where sdoc.id=y.doc;

    update stdoc set qty = y.qty_sum, total = y.total
        from (select doc, sum(qty) qty_sum, sum(qty*price) total from stentry group by doc) y
        where stdoc.id=y.doc;

    update dldoc set qty = y.qty_sum, total = y.total
        from (select doc, sum(qty) qty_sum, sum(qty*price) total from dlentry group by doc) y
        where dldoc.id=y.doc;

    -- Review later.
    update acdoc set ndebit = valp, ncredit = -valn
        from (select doc, 
                sum(case when val>=0 then val else 0 end) valp,
                sum(case when val<0 then val else 0 end) valn
            from acentry group by doc) y
        where acdoc.id=y.doc;
