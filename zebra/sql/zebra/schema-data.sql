--\import lily.base.schema
--\import lily.base.cat
--\import lily.base.phase
--\import lily.base.parm
--\import lily.base.tagv
--\import lily.base.tag
--\import lily.base.form
--\import lily.base.rlock

    insert into schema(id, code, label) values(1, 'post',   'Topic Post');
    insert into schema(id, code, label) values(2, 'reply',  'Topic Reply');
    insert into schema(id, code, label) values(3, 'opp',    'Opportunity');
    insert into schema(id, code, label) values(4, 'trk',    'Opportunity Tracking');
    insert into schema(id, code, label) values(5, 'ques',   'Question');
    insert into schema(id, code, label) values(6, 'ans',    'Answer');
    insert into schema(id, code, label) values(7, 'wlog',   'Work Log');
    insert into schema(id, code, label) values(8, 'acc',    'Accounting');
    insert into schema(id, code, label) values(9, 'file',   'File Info');
    insert into schema(id, code, label) values(10, 'part',  'Contact Party');
    insert into schema(id, code, label) values(11, 'art',   'Artifact');
    insert into schema(id, code, label) values(12, 'stok',  'Stocking');
    insert into schema(id, code, label) values(13, 'fab',   'Fabrication');

    insert into cat(id, schema, code, label, description) values(301, 3, 'opp', '机会', '潜在的或已实现的销售机会');
    insert into cat(id, schema, code, label, description) values(302, 3, 'job', '任务', '指派的工作任务');
    insert into cat(id, schema, code, label, description) values(401, 4, 'trk', '跟进', '机会/项目的跟进');
    insert into cat(id, schema, code, label, description) values(701, 7, 'log', '日志', '记录工作的内容');
    insert into cat(id, schema, code, label, description) values(702, 7, 'plan', '计划', '计划将来要做的事情');

    insert into cat(id, schema, code, label, description) values(1201, 12, 'INIT', '初始化', '');
    insert into cat(id, schema, code, label, description) values(1202, 12, 'TK_I', '采购入库', '');
    insert into cat(id, schema, code, label, description) values(1203, 12, 'TK_O', '销售出库', '');
    insert into cat(id, schema, code, label, description) values(1204, 12, 'XFRO', '调拨出库', '');
    insert into cat(id, schema, code, label, description) values(1205, 12, 'XFRI', '调拨入库', '');
    insert into cat(id, schema, code, label, description) values(1206, 12, 'OSPO', '委外出库', '');
    insert into cat(id, schema, code, label, description) values(1207, 12, 'OSPI', '委外入库', '');
    insert into cat(id, schema, code, label, description) values(1208, 12, 'STKD', '盘盈盘亏', '');
    insert into cat(id, schema, code, label, description) values(1209, 12, 'DAMG', '物料报损', '');
    insert into cat(id, schema, code, label, description) values(1210, 12, 'LOCK', '库存锁定', '');
    insert into cat(id, schema, code, label, description) values(1211, 12, 'TKFI', '生产入库', '');
    insert into cat(id, schema, code, label, description) values(1212, 12, 'TKFO', '生产出库', '');

    insert into phase(id, schema, code, label, priority) values(301, 3, 'init', '初步信息采集', 10);
    insert into phase(id, schema, code, label, priority) values(302, 3, 'chat', '初步沟通', 20);
    insert into phase(id, schema, code, label, priority) values(303, 3, 'meet', '交涉中/走访中', 30);
    insert into phase(id, schema, code, label, priority) values(304, 3, 'well', '已确定意向/已报价', 40);
    insert into phase(id, schema, code, label, priority) values(305, 3, 'paid', '合同签订/已付款', 50);

    insert into phase(id, schema, code, label, priority) values(1201, 12, 'norm', '正常', 10);
    insert into phase(id, schema, code, label, priority) values(1202, 12, 'susp', '暂停/挂起', 20);
    insert into phase(id, schema, code, label, priority) values(1203, 12, 'stck', '盘点中', 30);
    insert into phase(id, schema, code, label, priority) values(1204, 12, 'txfr', '调拨中', 40);
    insert into phase(id, schema, code, label, priority) values(1205, 12, 'chck', '校验中', 50);

    insert into parm(id, schema, code, label) values(1, 3, 'addr', '地址');
    insert into parm(id, schema, code, label) values(2, 4, 'cost', '开支');

    insert into tagv(id, schema, code, label) values(1, 3, 'JHLY', '机会来源');
    insert into tag(id, tagv, label) values(100, 1, '朋友介绍');
    insert into tag(id, tagv, label) values(101, 1, '客户介绍');
    insert into tag(id, tagv, label) values(102, 1, '独立开发');
    insert into tag(id, tagv, label) values(103, 1, '媒体宣传');
    insert into tag(id, tagv, label) values(104, 1, '电话来访');
    insert into tag(id, tagv, label) values(105, 1, '促销活动');
    insert into tag(id, tagv, label) values(106, 1, '老客户');
    insert into tag(id, tagv, label) values(107, 1, '代理商');
    insert into tag(id, tagv, label) values(108, 1, '合作伙伴');
    insert into tag(id, tagv, label) values(109, 1, '公开招标');
    insert into tag(id, tagv, label) values(110, 1, '互联网');

    insert into tagv(id, schema, code, label) values(2, 4, 'QTLX', '洽谈类型');
    insert into tag(id, tagv, label) values(200, 2, '电话洽谈');
    insert into tag(id, tagv, label) values(201, 2, '面谈');
    insert into tag(id, tagv, label) values(202, 2, '走访');
    insert into tag(id, tagv, label) values(203, 2, '通过互联网');
    insert into tag(id, tagv, label) values(204, 2, '信件洽谈');

    insert into tagv(id, schema, code, label) values(3, 3, 'GHFS', '供货方式');
    insert into tag(id, tagv, label) values(300, 3, '甲订甲供');
    insert into tag(id, tagv, label) values(301, 3, '甲订乙供');
    insert into tag(id, tagv, label) values(302, 3, '乙订乙供');

    insert into tagv(id, schema, code, label) values(4, 3, 'CGYZ', '采购原则');
    insert into tag(id, tagv, label) values(400, 4, '品牌优先');
    insert into tag(id, tagv, label) values(401, 4, '价格优先');
    insert into tag(id, tagv, label) values(402, 4, '性价比优先');

    insert into tagv(id, schema, code, label) values(5, 9, 'WJXX', '文件信息');

    insert into form(id, schema, code, label, subject) values(800, 8, 'init', '期初登记单', '[期初登记单] ');
    insert into form(id, schema, code, label, subject) values(801, 8, 'pay',  '通用付款单', '[付款单] ');
    insert into form(id, schema, code, label, subject) values(802, 8, 'recv', '通用收款单', '[收款单] ');
    insert into form(id, schema, code, label, subject) values(803, 8, 'trip', '差旅费报销单', '[差旅费报销单] ');
    insert into form(id, schema, code, label, subject) values(804, 8, 'rcpt', '借条', '[借条收据] ');

    insert into rlock(schema)   select id from schema   where id<100;
    insert into rlock(cat)      select id from cat      where id<10000;
    insert into rlock(phase)    select id from phase    where id<10000;
    insert into rlock(parm)     select id from parm      where id<10000;
    insert into rlock(tagv)     select id from tagv     where id<10000;
    insert into rlock(form)     select id from form     where id<10000;
