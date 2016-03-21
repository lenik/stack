--\import zebra.oa.contact
--\import zebra.oa.org
--\import zebra.oa.orgunit
--\import zebra.oa.person
    create or replace view v_contact as
        select org.label "org_label",
            oo.label || ' - ' || ou.label "ou_label",
            p.label "person_label", c.*
        from contact c
            left join org           on c.org=org.id
            left join orgunit ou    on c.ou=ou.id
            left join org oo        on ou.org=oo.id
            left join person p      on c.person=p.id;
