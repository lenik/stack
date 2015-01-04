package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sea.FooVbo;

public class TopicVbo
        extends FooVbo<Topic> {

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
            author.text(reply.getOp().getFullName() + " @" + reply.getLastModifiedDate());

            out.hr();
        }
        return out;
    }

}
