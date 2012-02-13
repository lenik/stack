
alter table unit add column hint varchar(20) not null default '';
update unit set hint='长度' where id='m' or std_unit='m';
update unit set hint='面积' where id='m2' or std_unit='m2';
update unit set hint='体积' where id='m3' or std_unit='m3';
update unit set hint='质量' where id='g' or std_unit='g';
update unit set hint='力' where id='N' or std_unit='N';
update unit set hint='容量' where id='L' or std_unit='mL';
update unit set hint='数量' where id='PCS' or std_unit='PCS';
update unit set hint='数量' where hint='';

alter table material alter column unit_hint drop not null;
update material set unit_hint=null;

alter table stock_warehouse alter column name drop not null;
update stock_warehouse set label=name;
update stock_warehouse set name=null;

update stock_location set label=address;
alter table stock_location drop column address;

