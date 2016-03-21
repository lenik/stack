--\import zebra.io.stentry

    create or replace view v_stentry as
        select a.*,
            stdoc.subject "doc_subject",
            art.label "art_label",
            place.label "place_label"
        from stentry a
            left join stdoc on a.doc=stdoc.id
            left join art on a.art=art.id
            left join place on a.place=place.id;
