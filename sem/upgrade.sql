-- 1
alter table stock_order_item rename column batch to batch0;
alter table stock_order_item drop column cbatch;

alter table purchase_request_item drop column parent;
alter table purchase_request_item rename column purchase_request to parent;

alter table purchase_request_item rename column quantity to required_quantity;
alter table purchase_request_item rename column plan_quantity to quantity;

alter table purchase_inquiry drop column parent;
alter table purchase_inquiry rename column purchase_request_item to parent;

-- 2
alter table make_order_item rename column external_specification to external_model_spec;

-- 3
drop table plover_conf;

update material_category set label=name;
alter table material_category drop column name;
alter table palette_entry drop column name;
alter table acl drop column name;

-- 4
update chance_competitor set label=name;
alter table chance_competitor drop column name;

-- 5

ALTER TABLE employee_info DROP COLUMN title;
ALTER TABLE employee_info DROP COLUMN role;
ALTER TABLE employee_info DROP COLUMN education;
ALTER TABLE employee_info DROP COLUMN job_performance;

drop table job_title;
drop table job_post;
drop table job_performance;
drop table person_education_type;

drop table person_skill;
drop table person_skill_category;

-- 6: Separate material* from sem-inventory.

    -- stockwarehouse
        update r_ace set q_name='entity:semmaterial-unit:Stoc_dqKNg.' where q_name='entity:seminventory-unit:Stoc_MwV6i.';

    -- stocklocation
        update r_ace set q_name='entity:semmaterial-unit:Stoc_eXMFC.' where q_name='entity:seminventory-unit:Stoc_Bv28S.';

    -- material
        update r_ace set q_name='entity:semmaterial-unit:Mate_Psiq4.' where q_name='entity:seminventory-unit:Mate_8ZplY.';

    -- material_category
        update r_ace set q_name='entity:semmaterial-unit:Mate_ZkPet.' where q_name='entity:seminventory-unit:Mate_N6R6X.';
