--\import lily.account
--\import zebra.acc.account
--\import zebra.acc.acdoc
--\import zebra.oa.org
--\import zebra.oa.person

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
