package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class TopicVbo
        extends FooMesgVbo<Topic> {

    public TopicVbo() {
        super(Topic.class);
    }

    @Override
    protected IHtmlTag afterForm(IHtmlViewContext ctx, IHtmlTag out, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        ReplyMapper replyMapper = ctx.query(ReplyMapper.class);

        Topic topic = ref.get();

        ReplyCriteria criteria = new ReplyCriteria();
        criteria.topicId = topic.getId();

        List<Reply> replies = replyMapper.filter(criteria);
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

        HtmlFormTag form = out.form().method("post").action("../");
        {
            form.input().type("hidden").name("mode").value("reply");
            form.input().type("hidden").name("topic").value("" + topic.getId());

            IHtmlTag tab = form.table().class_("zu-msg");
            IHtmlTag textLine = tab.tr().id("zp-reply");
            {
                HtmlLabelTag textLabel = textLine.th().label();
                textLabel.span().class_("fa icon").text(FA_FILE_O);
                textLabel.text("跟进: ");

                HtmlTextareaTag textarea = textLine.td().textarea().name("text");
                textarea.placeholder("输入跟踪信息…");
            }

            HtmlDivTag author = form.div().class_("zu-author");
            author.span().class_("fa icon").text(FA_COMMENT_O);
            author.text(" -- The Magic Person");

            HtmlDivTag div = form.div();
            HtmlButtonTag submitButton = div.button().type("submit");
            submitButton.span().class_("fa icon").text(FA_CHECK);
            submitButton.text("提交");

            HtmlInputTag resetButton = div.input().type("reset");
            resetButton.value("清空");
        }

        return out;
    }

}
