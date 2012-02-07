
update party set label=name;
update org_unit set label=name;
alter party drop column name;
alter org_unit drop column name;

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
