-- set default gid0 = min(group) from group_user.

    update "user"
        set gid0 = a.group_min
        from (select "user", min("group") "group_min" from group_user group by "user") a
        where id="user" and gid0 is null;

-- set default gids.
    update diary    set gid=gid0 from "user" u where uid=u.id and gid is null;
    update topic    set gid=gid0 from "user" u where uid=u.id and gid is null;
    update reply    set gid=gid0 from "user" u where uid=u.id and gid is null;
    update fileinfo set gid=gid0 from "user" u where uid=u.id and gid is null;
    update art      set gid=gid0 from "user" u where uid=u.id and gid is null;
    update acdoc    set gid=gid0 from "user" u where uid=u.id and gid is null;
    update sdoc     set gid=gid0 from "user" u where uid=u.id and gid is null;

