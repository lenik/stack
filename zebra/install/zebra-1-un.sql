    drop view v_topic;
    drop view v_reply;
    drop table replytag;
    drop table topictag;
    drop table topicatt;
    drop table topicparty;
    drop table reply;
    drop table topic;
    
    drop view v_cell;
    drop table artcell;
    drop table artwopt;
    drop table art;
    drop table artcat;
    drop table cell;
    drop table warehouse;
    drop type CellUsage;
    drop type SupplyMethod;
    
    drop view v_contact;
    drop view v_personrole;
    alter table contact drop constraint contact_org_fk_org;
    alter table contact drop constraint contact_org_fk_ou;
    alter table contact drop constraint contact_org_fk_person;
    drop table personrole;
    drop table person;
    drop table orgunit;
    drop table org;
    drop table contact;
    
    drop table "group_user";
    drop table "user";
    drop table "group";

-- sequences
    
    drop sequence user_seq;
    drop sequence group_seq;
    drop sequence contact_seq;
    drop sequence org_seq;
    drop sequence orgunit_seq;
    drop sequence person_seq;

    drop sequence warehouse_seq;
    drop sequence cell_seq;
    drop sequence artcat_seq;
    drop sequence art_seq;
    drop sequence artcell_seq;
    drop sequence artwopt_seq;

