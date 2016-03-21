--\import zebra.oa.topic
--\import zebra.oa.reply
--\import zebra.oa.diary
--\import zebra.acc.acdoc
--\import zebra.io.sdoc
--\import zebra.io.stdoc
--\import zebra.io.dldoc

    create or replace view v_log as
              select 'topic' c, id, creation, mode, uid, gid, op, form,       cat, subject "msg", t0 from topic
        union select 'reply' c, id, creation, mode, uid, gid, op, null "form",cat, text, t0 from reply
        union select 'diary' c, id, creation, mode, uid, gid, op, form,       cat, subject, t0 from diary
        union select 'acdoc' c, id, creation, mode, uid, gid, op, form,       cat, subject, t0 from acdoc
        union select 'sdoc'  c, id, creation, mode, uid, gid, op, form,       cat, subject, t0 from sdoc
        union select 'stdoc' c, id, creation, mode, uid, gid, op, form,       cat, subject, t0 from stdoc
        union select 'dldoc' c, id, creation, mode, uid, gid, op, form,       cat, subject, t0 from dldoc;
