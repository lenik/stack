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

-- 7: Refactor user passwords, save encrypted passwords.
    -- all normal samples => "secret"
    update user_password set passwd='c2VjcmV0' where "user" in
        (select id from principal where name in
            ('tom', 'guest', 'kate', 'wall-e', 'sun', 'alice', 'eva')
        );

    -- Rewrite password "Bee32"
    update user_password set passwd='QmVlMzI=' where passwd='f58d2e196e0c9af5cc261fda9fd6761822ee5f08';
    -- Rewrite password "guest"
    update user_password set passwd='Z3Vlc3Q=' where passwd='35675e68f4b5af7b995d9205ad0fc43842f16450';
    -- Rewrite password "secret"
    update user_password set passwd='c2VjcmV0' where passwd='e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4';
    -- Rewrite password "168168"
    update user_password set passwd='MTY4MTY4' where passwd='8d4916ac3fc6e1385213fe6210b06331a9fc8f6f';
    -- Rewrite password "000000"
    update user_password set passwd='MDAwMDAw' where passwd='c984aed014aec7623a54f0591da07a85fd4b762d';
    -- Rewrite password "123123"
    update user_password set passwd='MTIzMTIz' where passwd='601f1889667efaebb33b8c12572835da3f027f78';
    -- Rewrite password "888888"
    update user_password set passwd='ODg4ODg4' where passwd='1f82c942befda29b6ed487a51da199f78fce7f05';
    -- Rewrite password "13666670345"
    update user_password set passwd='MTM2NjY2NzAzNDU=' where passwd='bca4a834f2da5698e37d008b758c5dccd0d24c7d';
    -- Rewrite password "hmzy888888"
    update user_password set passwd='aG16eTg4ODg4OA==' where passwd='846c88e7f976c7cc57bb95c74e98946dd10aeb4e';
    -- Rewrite password "15967301888"
    update user_password set passwd='MTU5NjczMDE4ODg=' where passwd='ec0fe724a0ffd1915629bac2103d05dc5db58536';
    -- Rewrite password "fya1888888"
    update user_password set passwd='ZnlhMTg4ODg4OA==' where passwd='7b2aa85274ddff5dff55edc5bb16ccf26afccc9e';

    -- Reset unknown passwords to '278591'
    update user_password set passwd='Mjc4NTkx' where length(passwd)=40;
