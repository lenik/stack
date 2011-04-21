package com.bee32.sem.mail;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.mail.entity.Mail;
import com.bee32.sem.mail.entity.MailBox;
import com.bee32.sem.mail.entity.MailCopy;
import com.bee32.sem.mail.entity.MailFilter;

@ImportUnit(IcsfIdentityUnit.class)
public class SEMMailUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Mail.class);
        add(MailCopy.class);
        add(MailBox.class);
        add(MailFilter.class);
    }

}
