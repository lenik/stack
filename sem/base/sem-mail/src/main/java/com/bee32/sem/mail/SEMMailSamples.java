package com.bee32.sem.mail;

import java.util.Arrays;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.mail.entity.Mail;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailParty;
import com.bee32.sem.mail.entity.MailFilter;
import com.bee32.sem.mail.entity.MailFolder;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMMailSamples
        extends EntitySamplesContribution {

    public static MailFolder favBox = new MailFolder();
    public static MailFolder spamBox = new MailFolder();

    public static MailFilter spamFilter = new MailFilter();

    public static Mail hello = new Mail();
    public static Mail helloEcho = new Mail();

    static {
        favBox.setName("fav");
        favBox.setLabel("收藏夹");
        favBox.setPriority(MailFolder.PRIORITY_HIGH);

        spamBox.setName("spam");
        spamBox.setLabel("垃圾箱");
        spamBox.setColor(MailFolder.PRIORITY_LOW);

        spamFilter.setName("spam-filter");
        spamFilter.setDescription("将标记为'垃圾'的邮件移动到'垃圾箱'");
        spamFilter.setExpr("a.is-spam...");
        spamFilter.setSource(null); // in-box only
        spamFilter.setTarget(spamBox);

        hello.setFromUser(IcsfPrincipalSamples.wallE);
        hello.setToUser(IcsfPrincipalSamples.eva);
        hello.setSubject("Hello!");
        hello.setBody("Hello, world!\n\n这句话的意思是：朋友、再见！");

        MailDelivery helloSend = new MailDelivery(hello, MailParty.FROM);
        // helloSend.setSentDate();
        MailDelivery helloRecv = new MailDelivery(hello, MailParty.TO);
        hello.setCopies(Arrays.asList(helloSend, helloRecv));

        helloEcho.setFromUser(IcsfPrincipalSamples.eva);
        helloEcho.setToUser(IcsfPrincipalSamples.wallE);
        helloEcho.setReferrer(hello);
        helloEcho.setSubject("Hi, 我收到了你的 Hello！");
        helloEcho.setBody("谢谢你告诉我 Hello, world 的真正含义！\n" + //
                "在此之前，我一直以为它是\"你好、世界\"，现在我才知道自己的愚蠢。");
        // helloEcho.setCc("");

        MailDelivery helloEchoSend = new MailDelivery(helloEcho, MailParty.FROM);
        MailDelivery helloEchoRecv = new MailDelivery(helloEcho, MailParty.TO);
        helloEcho.setCopies(Arrays.asList(helloEchoSend, helloEchoRecv));
    }

    @Override
    protected void preamble() {
        addNormalSample(favBox);
        addNormalSample(spamBox);
        addNormalSample(spamFilter);
        addNormalSample(hello);
        addNormalSample(helloEcho);
    }

}
