--\import lily.account

    create sequence artcat_seq start with 1000;
    create table artcat(
        id          int primary key default nextval('artcat_seq'),
--\mixin lily.mixin.Acl_rw-r--r--
--\mixin lily.mixin.LabelExVer

        parent      int
            references artcat(id) on update cascade on delete cascade,

        depth       int not null default -1,
        skufmt      varchar(100),
        barfmt      varchar(100),
        count       int not null default 0 -- redundant.
    );

    create index artcat_label       on artcat(label);
    create index artcat_lastmod     on artcat(lastmod desc);
    create index artcat_priority    on artcat(priority);
    create index artcat_state       on artcat(state);
    create index artcat_uid_acl     on artcat(uid, acl);
