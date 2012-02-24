package com.bee32.sem.mail;

import java.util.Arrays;

import javax.inject.Inject;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.mail.entity.Mail;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailFilter;
import com.bee32.sem.mail.entity.MailFolder;
import com.bee32.sem.mail.entity.MailOrientation;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMMailSamples
        extends SampleContribution {

    @Inject
    IcsfPrincipalSamples principals;

    public MailFilter spamFilter = new MailFilter();

    public Mail hello = new Mail();
    public Mail helloEcho = new Mail();

    @Override
    protected void preamble() {
        spamFilter.setLabel("spam-filter");
        spamFilter.setDescription("将标记为'垃圾'的邮件移动到'垃圾箱'");
        spamFilter.setExpr("a.is-spam...");
        spamFilter.setSource(null); // in-box only
        spamFilter.setTarget(MailFolder.SPAMBOX);

        hello.setFromUser(principals.wallE);
        hello.addRecipientUser(principals.eva);
        hello.setSubject("Hello!");
        hello.setBody("Hello, world!\n\n这句话的意思是：朋友、再见！");

        MailDelivery helloSend = new MailDelivery(hello, MailOrientation.FROM);
        helloSend.setFolder(MailFolder.OUTBOX);
        // helloSend.setSentDate();
        MailDelivery helloRecv = new MailDelivery(hello, MailOrientation.RECIPIENT);
        helloRecv.setFolder(MailFolder.INBOX);

        hello.setDeliveries(Arrays.asList(helloSend, helloRecv));

        helloEcho.setFromUser(principals.eva);
        helloEcho.addRecipientUser(principals.wallE);
        helloEcho.setReferrer(hello);
        helloEcho.setSubject("Hi, 我收到了你的 Hello！");
        helloEcho.setBody("谢谢你告诉我 Hello, world 的真正含义！\n" + //
                "在此之前，我一直以为它是\"你好、世界\"，现在我才知道自己的愚蠢。");
        // helloEcho.setCc("");

        MailDelivery helloEchoSend = new MailDelivery(helloEcho, MailOrientation.FROM);
        MailDelivery helloEchoRecv = new MailDelivery(helloEcho, MailOrientation.RECIPIENT);
        helloEcho.setDeliveries(Arrays.asList(helloEchoSend, helloEchoRecv));

        add(spamFilter);
        add(hello);
        add(helloEcho);
    }

}
