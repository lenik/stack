--\import zebra.oa.personrole

    create or replace view v_personrole as
        select o.label "org_label", ou.label "ou_label", p.label "person_label", r.*
        from personrole r
            left join person p on r.person=p.id
            left join orgunit ou on r.ou=ou.id
            left join org o on r.org=o.id;
