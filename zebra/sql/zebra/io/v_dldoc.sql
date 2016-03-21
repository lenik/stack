--\import zebra.io.dldoc

    create or replace view v_dldoc as
        select a.*,
            prev.subject "prev_subject",
            sdoc.subject "sdoc_subject",
            op.label     "op_label",
            org.label    "org_label",
            person.label "person_label",
            cat.label    "cat_label",
            phase.label  "phase_label"
        from dldoc a
            left join dldoc prev on a.prev=prev.id
            left join sdoc on a.sdoc=sdoc.id
            left join "user" op on a.op=op.id
            left join org on a.org=org.id
            left join person on a.person=person.id
            left join cat on a.cat=cat.id
            left join phase on a.phase=phase.id;

    create or replace view v_dldoc_n as select
        (select count(*) from dldoc) "total",
        (select count(*) from dldoc where t1 is null) "shipping";
