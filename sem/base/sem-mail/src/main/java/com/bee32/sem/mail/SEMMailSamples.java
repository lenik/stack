package com.bee32.sem.mail;

import java.util.Arrays;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.mail.entity.Mail;
import com.bee32.sem.mail.entity.MailBox;
import com.bee32.sem.mail.entity.MailCopy;
import com.bee32.sem.mail.entity.MailFilter;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMMailSamples
        extends EntitySamplesContribution {

    public static MailBox favBox = new MailBox();
    public static MailBox spamBox = new MailBox();

    public static MailFilter spamFilter = new MailFilter();

    public static Mail hello = new Mail();
    public static Mail helloEcho = new Mail();

    static {
        favBox.setName("fav");
        favBox.setDisplayName("收藏夹");
        favBox.setPriority(MailBox.PRIORITY_HIGH);

        spamBox.setName("spam");
        spamBox.setDisplayName("垃圾箱");
        spamBox.setColor(MailBox.PRIORITY_LOW);

        spamFilter.setName("spam-filter");
        spamFilter.setDescription("将标记为'垃圾'的邮件移动到'垃圾箱'");
        spamFilter.setExpr("a.is-spam...");
        spamFilter.setSource(null); // in-box only
        spamFilter.setTarget(spamBox);

        hello.setFromUser(IcsfPrincipalSamples.wallE);
        hello.setToUser(IcsfPrincipalSamples.eva);
        hello.setSubject("Hello!");
        hello.setBody("Hello, world!\n\n这句话的意思是：朋友、再见！");

        MailCopy helloSend = new MailCopy(hello, MailOwnerType.FROM_USER);
        // helloSend.setSentDate();
        MailCopy helloRecv = new MailCopy(hello, MailOwnerType.TO_USER);
        hello.setCopies(Arrays.asList(helloSend, helloRecv));

        helloEcho.setFromUser(IcsfPrincipalSamples.eva);
        helloEcho.setToUser(IcsfPrincipalSamples.wallE);
        helloEcho.setReferrer(hello);
        helloEcho.setSubject("Hi, 我收到了你的 Hello！");
        helloEcho.setBody("谢谢你告诉我 Hello, world 的真正含义！\n" + //
                "在此之前，我一直以为它是\"你好、世界\"，现在我才知道自己的愚蠢。");
        // helloEcho.setCc("");

        MailCopy helloEchoSend = new MailCopy(helloEcho, MailOwnerType.FROM_USER);
        MailCopy helloEchoRecv = new MailCopy(helloEcho, MailOwnerType.TO_USER);
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
