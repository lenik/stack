package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.err.Err;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.bas.ui.dom1.UiValue;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.security.LoginContext;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.oa.accnt.impl.AccountingEventMapper;
import com.bee32.zebra.oa.accnt.impl.AccountingEventMask;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.hbin.DataTable;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class Topic_htm
        extends SlimMesgForm_htm<Topic> {

    public Topic_htm() {
        super(Topic.class);
    }

    @Override
    protected void process(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Topic> ref)
            throws ViewBuilderException, IOException {
        HttpServletRequest req = ctx.getRequest();

        String topicIdStr = req.getParameter("topic");
        if (topicIdStr != null) {
            int topicId = Integer.parseInt(topicIdStr);
            Topic topicRef = new Topic();
            topicRef.setId(topicId);

            Reply reply = new Reply(topicRef, null);
            try {
                // data.populate(ctx.getRequest().getParameterMap());
                inject(UiValue.wrap(reply), ctx.getRequest().getParameterMap());
                reply.persist(ctx, out);

                HtmlDiv alert = out.div().class_("alert alert-success");
                alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
                alert.span().class_("fa icon").text(FA_CHECK_CIRCLE);
                alert.strong().text("[成功]");
                alert.text("您刚刚添加了跟进信息。");
            } catch (Throwable e) {
                e.printStackTrace();
                e = Err.unwrap(e);
                HtmlDiv alert = out.div().class_("alert alert-danger");
                alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
                alert.span().class_("fa icon").text(FA_TIMES_CIRCLE);
                alert.strong().text("[错误]");
                alert.text("无法创建新记录: " + e.getMessage());
            }
            return;
        }

        super.process(ctx, out, ref);
    }

    @Override
    protected void buildFormHead(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> ref) {
        super.buildFormHead(ctx, out, ref);
        Topic topic = (Topic) ref.get();
        if (topic.getId() != null) {
            TopicMapper topicMapper = ctx.query(TopicMapper.class);
            int n = topicMapper.replyCount(topic.getId());
            HtmlA link = out.a().class_("fa").href("#reply-tree").text(FA_ANGLE_DOUBLE_RIGHT);
            if (n == 0) {
                link.text(" 本项目尚无跟进。");
            } else {
                link.text(" 本项目已有 " + n + " 条跟进信息。");
            }
        }
    }

    @Override
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Topic> ref)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();
        if (topic.getId() != null) {
            IHtmlOut sect;

            sect = new SectionDiv_htm1("收支情况", IFontAwesomeCharAliases.FA_USD).build(out, "s-acc");
            buildAccList(ctx, sect, ref);

            sect = new SectionDiv_htm1("跟进", IFontAwesomeCharAliases.FA_COMMENTS_O).build(out, "s-reply");
            buildReplyTree(ctx, sect, ref);
            buildReplyForm(ctx, sect, ref);
        }
        return out;
    }

    protected void buildAccList(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Topic> ref)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        AccountingEventMask mask = new AccountingEventMask();
        mask.topicId = topic.getId();

        AccountingEventMapper eventMapper = ctx.query(AccountingEventMapper.class);
        List<AccountingEvent> events = eventMapper.filter(mask);

        HtmlTbody tbody = new DataTable("凭证", "类型", "日期", "金额", "说明", "记账").build(out, "acdocs");

        for (AccountingEvent event : events) {
            FormDef form = event.getForm().getDef();
            HtmlTr tr = tbody.tr();

            tr.id("acdoc-" + event.getId());
            tr.td().a().href("_blank", _webApp_ + "/acdoc/" + event.getId() + "/").text("[ac" + event.getId() + "] ");
            tr.td().text(form == null ? "-" : form.getLabel());
            tr.td().text(Dates.YYYY_MM_DD.format(event.getBeginDate()));
            tr.td().text(event.getValue());
            tr.td().text(event.getSubject());
            // tr.td().iText(FA_SPINNER, "fa fa-spin");
            tr.td().iText(FA_MINUS, "fa");
        }
    }

    protected void buildReplyTree(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Topic> ref)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        ReplyMask mask = new ReplyMask();
        mask.topicId = topic.getId();

        ReplyMapper replyMapper = ctx.query(ReplyMapper.class);
        List<Reply> replies = replyMapper.filter(mask);

        out.div().id("reply-tree");
        for (Reply reply : replies) {
            HtmlDiv div = out.div().id("reply-" + reply.getId()).class_("zu-reply");

            HtmlDiv mesg = div.div().class_("zu-message");
            mesg.span().class_("zu-nvote").text(reply.getClickInfo().getVoteCount());
            mesg.text(reply.getText());

            List<Person> parties = reply.getParties();
            if (parties != null && !parties.isEmpty()) {
                HtmlUl ul = mesg.ul();
                for (Person party : parties)
                    ul.li().text(party.getFullName());
            }

            HtmlDiv author = div.div().class_("zu-author");
            author.span().class_("fa icon").text(FA_COMMENT_O);
            author.text(reply.getOp().getFullName() + " @" + reply.getLastModifiedDate());

            out.hr();
        }
    }

    protected void buildReplyForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<Topic> ref)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        // Reply reply = new Reply(topic, null);
        // UiVar<Reply> replyRef = UiVar.wrap(reply);
        // super.buildForm(ctx, out, replyRef);

        HtmlForm form = out.form().id("reply-form").method("post").action("#reply-form");
        form.input().type("hidden").name("topic").value(topic.getId());

        IHtmlOut tab = form.table().class_("zu-msg");
        IHtmlOut textLine = tab.tr().id("zp-reply").class_("noprint");
        {
            HtmlLabel textLabel = textLine.th().label();
            textLabel.span().class_("fa icon").text(FA_FILE_O);
            textLabel.text("跟进: ");

            HtmlTextarea textarea = textLine.td().textarea().name("text");
            textarea.placeholder("输入跟踪信息…");
        }

        HtmlDiv author = form.div().class_("zu-author");
        author.span().class_("fa icon").text(FA_COMMENT_O);
        User user = LoginContext.fromSession().user;
        author.text(user.getFullName());

        HtmlDiv div = form.div();
        HtmlButton submitButton = div.button().type("submit");
        submitButton.span().class_("fa icon").text(FA_CHECK);
        submitButton.text("提交");

        HtmlInput resetButton = div.input().type("reset");
        resetButton.value("清空");
    }

}
