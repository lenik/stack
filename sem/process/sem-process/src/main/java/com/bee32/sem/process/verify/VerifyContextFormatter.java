package com.bee32.sem.process.verify;

import java.util.Set;

import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.ObjectFormatter;
import com.bee32.plover.util.PrettyPrintStream;

public class VerifyContextFormatter
        extends ObjectFormatter<AbstractVerifyContext> {

    public VerifyContextFormatter() {
        super();
    }

    public VerifyContextFormatter(PrettyPrintStream out, Set<Object> occurred) {
        super(out, occurred);
    }

    @Override
    protected void formatId(FormatStyle format, AbstractVerifyContext context) {
        VerifyEvalState state = context.getVerifyEvalState();
        boolean locked = context.isLocked();
        char lockChar = locked ? '*' : '-';
        out.print('C');
        out.print(lockChar);
        out.print(state);
    }

}
