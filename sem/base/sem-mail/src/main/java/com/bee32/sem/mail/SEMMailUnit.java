package com.bee32.sem.mail;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.mail.entity.Mail;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailFilter;
import com.bee32.sem.mail.entity.MailFolder;
import com.bee32.sem.mail.entity.Message;

@ImportUnit(IcsfAccessUnit.class)
public class SEMMailUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Message.class);

        add(Mail.class);
        add(MailDelivery.class);
        add(MailFolder.class);
        add(MailFilter.class);
    }

}
