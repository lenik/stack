--\import zebra.io.place

    create or replace view v_place as
        select place.*, p.label "parent_label"
        from place
            left join place p on place.parent=p.id;

    create or replace view v_place_n as select
        (select count(*) from place) total,
        (select count(distinct place) from placeopt) used,
        (select count(distinct place) from placeopt where locked) locked;
