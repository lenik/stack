--\import zebra.io.art

    create or replace view v_art_n as
        select
            (select count(*) from art) total,
            (select count(distinct art) from stentry) used;
