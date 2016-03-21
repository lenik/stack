--\import zebra.io.stinit

    create or replace view v_stinits as
        select year, art, sum(qty) from stinit group by year, art;

    create or replace view v_stinit as
        select a.id, a.year,
            a.art, r.label "art_label",
            a.place, p.label "place_label",
            a.batch, a.divs, a.qty
        from stinit a
            left join art r on a.art=r.id
            left join place p on a.place=p.id;
