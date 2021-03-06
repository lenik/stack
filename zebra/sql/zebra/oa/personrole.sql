--\import zebra.oa.org
--\import zebra.oa.orgunit
--\import zebra.oa.person

    create sequence personrole_seq;
    create table personrole(
        id          int primary key default nextval('personrole_seq'),
        org         int not null
            references org(id) on update cascade on delete cascade,

        ou          int
            references orgunit(id) on update cascade on delete cascade,

        person      int not null
            references person(id) on update cascade on delete cascade,

        role        varchar(20),
        description varchar(200),

        constraint personrole_uk unique(org, ou, person)
    );

--\import zebra.oa.personrole
