--\import zebra.io.stdoc

    create or replace view v_stdoc as
        select a.*,
            prev.subject "prev_subject",
            form.label   "form_label",
            topic.subject "topic_subject",
            op.label     "op_label",
            org.label    "org_label",
            person.label "person_label",
            cat.label    "cat_label",
            phase.label  "phase_label"
        from stdoc a
            left join stdoc prev on a.prev=prev.id
            left join form on a.form=form.id
            left join topic on a.topic=topic.id
            left join "user" op on a.op=op.id
            left join org on a.org=org.id
            left join person on a.person=person.id
            left join cat on a.cat=cat.id
            left join phase on a.phase=phase.id;

    create or replace view v_stdoc_n as
        select
            (select count(*) from stdoc) "total",
            (select count(*) from stdoc where phase>1201) "running";
