--\import zebra.acc.account
--\import zebra.oa.org
--\import zebra.oa.person

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
