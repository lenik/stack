--\import lily.account
--\import zebra.acc.acdoc
--\import zebra.oa.org
--\import zebra.oa.person

    create or replace view v_acdoc as
        select a.*, op.label "op_label", o.label "org_label", p.label "person_label",
            array(select e.account_label || '=' || e.val from v_acentry e where e.doc=a.id) "entries"
        from acdoc a
            left join org o on a.org=o.id
            left join person p on a.person=p.id
            left join "user" op on a.op=op.id;
