--\import zebra.io.dlentry

    create or replace view v_dlentry as
        select a.*,
            dldoc.subject "doc_subject",
            art.label "art_label",
            art.spec "art_spec"
        from dlentry a
            left join dldoc on a.doc=dldoc.id
            left join art on a.art=art.id;
