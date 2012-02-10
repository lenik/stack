
update party set label=name;
update org_unit set label=name;
alter table party drop column name;
alter table org_unit drop column name;

update r_ace set mode=replace(mode, 'l', '');
update r_ace set mode=replace(mode, 'S', 's');

update party set employee=true
    where id in (select distinct party from party_tags where tag='ENY');

update party set employee=false
    where id not in (select distinct party from party_tags where tag='ENY');

update party set customer=true
    where id in (select distinct party from party_tags where tag='CUS');

update party set customer=false
    where id not in (select distinct party from party_tags where tag='CUS');

update party set supplier=true
    where id in (select distinct party from party_tags where tag='SUP');

update party set supplier=false
    where id not in (select distinct party from party_tags where tag='SUP');

delete from party_tags where tag in ('CUS', 'SUP', 'ENY');

alter table material_category rename classification to material_type;
alter table account_ticket_item rename column parent to ticket;
alter table make_order_item rename column "order" to "parent";
alter table purchase_inquiry rename column org to supplier;
alter table purchase_request_item rename purchase_request to parent;

