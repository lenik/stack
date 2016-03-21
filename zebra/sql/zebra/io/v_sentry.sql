--\import zebra.io.sentry

    create or replace view v_sentry as
        select a.*,
            sdoc.subject "doc_subject",
            art.label "art_label",
            art.spec "art_spec"
        from sentry a
            left join sdoc on a.doc=sdoc.id
            left join art on a.art=art.id;
