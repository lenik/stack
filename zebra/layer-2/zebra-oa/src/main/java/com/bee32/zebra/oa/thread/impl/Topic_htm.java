package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.err.Err;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.bas.ui.dom1.UiValue;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.security.LoginContext;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.oa.accnt.AccountingEvent;
import com.bee32.zebra.oa.accnt.impl.AccountingEventCriteria;
import com.bee32.zebra.oa.accnt.impl.AccountingEventMapper;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.hbin.DataTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.SplitForm;
import com.bee32.zebra.tk.slim.SlimMesgForm_htm;

public class Topic_htm
        extends SlimMesgForm_htm<Topic> {

    public Topic_htm() {
        super(Topic.class);
    }

    @Override
    protected void process(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
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

                HtmlDivTag alert = out.div().class_("alert alert-success");
                alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
                alert.span().class_("fa icon").text(FA_CHECK_CIRCLE);
                alert.strong().text("[成功]");
                alert.text("您刚刚添加了跟进信息。");
            } catch (Throwable e) {
                e.printStackTrace();
                e = Err.unwrap(e);
                HtmlDivTag alert = out.div().class_("alert alert-danger");
                alert.a().class_("close").attr("data-dismiss", "alert").verbatim("&times;");
                alert.span().class_("fa icon").text(FA_TIMES_CIRCLE);
                alert.strong().text("[错误]");
                alert.text("无法创建新记录: " + e.getMessage());
            }
            return;
        }

        super.process(ctx, out, ref, options);
    }

    @Override
    protected boolean buildBasicGroup(IHtmlViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef, FieldDeclGroup group,
            IOptions options)
            throws ViewBuilderException {
        super.buildBasicGroup(ctx, out, instanceRef, group, options);

        Topic topic = (Topic) instanceRef.get();
        if (topic.getId() != null) {
            TopicMapper topicMapper = ctx.query(TopicMapper.class);
            int n = topicMapper.replyCount(topic.getId());
            SplitForm form = (SplitForm) out.getParent();
            HtmlATag link = form.head.a().class_("fa").href("#reply-tree").text(FA_ANGLE_DOUBLE_RIGHT);
            if (n == 0) {
                link.text(" 本项目尚无跟进。");
            } else {
                link.text(" 本项目已有 " + n + " 条跟进信息。");
            }
        }

        return false;
    }

    @Override
    protected IHtmlTag afterForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();
        if (topic.getId() != null) {
            SectionDiv section;

            section = new SectionDiv(out, "s-acc", "收支情况", IFontAwesomeCharAliases.FA_USD);
            buildAccList(ctx, section.contentDiv, ref, options);

            section = new SectionDiv(out, "s-reply", "跟进", IFontAwesomeCharAliases.FA_COMMENTS_O);
            buildReplyTree(ctx, section.contentDiv, ref, options);
            buildReplyForm(ctx, section.contentDiv, ref, options);
        }
        return out;
    }

    protected void buildAccList(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        AccountingEventCriteria criteria = new AccountingEventCriteria();
        criteria.topicId = topic.getId();

        AccountingEventMapper eventMapper = ctx.query(AccountingEventMapper.class);
        List<AccountingEvent> events = eventMapper.filter(criteria);

        DataTable tab = new DataTable(out).id("acdocs");
        tab.head.th().text("凭证");
        tab.head.th().text("类型");
        tab.head.th().text("日期");
        tab.head.th().text("金额");
        tab.head.th().text("说明");
        tab.head.th().text("记账");

        for (AccountingEvent event : events) {
            FormDef form = event.getForm();
            HtmlTrTag tr = tab.body.tr();

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

    protected void buildReplyTree(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        ReplyCriteria criteria = new ReplyCriteria();
        criteria.topicId = topic.getId();

        ReplyMapper replyMapper = ctx.query(ReplyMapper.class);
        List<Reply> replies = replyMapper.filter(criteria);

        out.div().id("reply-tree");
        for (Reply reply : replies) {
            HtmlDivTag div = out.div().id("reply-" + reply.getId()).class_("zu-reply");

            HtmlDivTag mesg = div.div().class_("zu-message");
            mesg.span().class_("zu-nvote").text(reply.getVoteCount());
            mesg.text(reply.getText());

            List<Person> parties = reply.getParties();
            if (parties != null && !parties.isEmpty()) {
                HtmlUlTag ul = mesg.ul();
                for (Person party : parties)
                    ul.li().text(party.getFullName());
            }

            HtmlDivTag author = div.div().class_("zu-author");
            author.span().class_("fa icon").text(FA_COMMENT_O);
            author.text(reply.getOp().getFullName() + " @" + reply.getLastModifiedDate());

            out.hr();
        }
    }

    protected void buildReplyForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        Topic topic = ref.get();

        // Reply reply = new Reply(topic, null);
        // UiVar<Reply> replyRef = UiVar.wrap(reply);
        // super.buildForm(ctx, out, replyRef, options);

        HtmlFormTag form = out.form().id("reply-form").method("post").action("#reply-form");
        form.input().type("hidden").name("topic").value(topic.getId());

        IHtmlTag tab = form.table().class_("zu-msg");
        IHtmlTag textLine = tab.tr().id("zp-reply").class_("noprint");
        {
            HtmlLabelTag textLabel = textLine.th().label();
            textLabel.span().class_("fa icon").text(FA_FILE_O);
            textLabel.text("跟进: ");

            HtmlTextareaTag textarea = textLine.td().textarea().name("text");
            textarea.placeholder("输入跟踪信息…");
        }

        HtmlDivTag author = form.div().class_("zu-author");
        author.span().class_("fa icon").text(FA_COMMENT_O);
        User user = LoginContext.fromSession().user;
        author.text(user.getFullName());

        HtmlDivTag div = form.div();
        HtmlButtonTag submitButton = div.button().type("submit");
        submitButton.span().class_("fa icon").text(FA_CHECK);
        submitButton.text("提交");

        HtmlInputTag resetButton = div.input().type("reset");
        resetButton.value("清空");
    }

}
