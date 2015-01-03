package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

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
            out.div().text(reply.getOp());
            out.div().text(reply.getText());
            out.hr();
        }
        return out;
    }

}
