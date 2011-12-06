package com.bee32.plover.ox1.dict;

import javax.free.IFormatter;
import javax.free.INegotiation;
import javax.free.NegotiationException;
import javax.free.StartswithPreorder;

import com.bee32.plover.util.FormatStyle;

public class DtoCodeTreeBuilder
        extends PoTreeBuilder<NameDictDto<?>, String> {

    public DtoCodeTreeBuilder() {
        super(DtoCodeMapper.INSTANCE, StartswithPreorder.getInstance());
        setFormatter(SimpleDtoFormatter.INSTANCE);
    }

    public static class SimpleDtoFormatter
            implements IFormatter<PoNode<?>> {

        @Override
        public String format(PoNode<?> node, INegotiation n)
                throws NegotiationException {
            return format(node);
        }

        @Override
        public String format(PoNode<?> node) {
            String key = (String) node.getKey();
            if (node.isVirtual())
                return key + " (virtual)";
            NameDictDto<?> dto = (NameDictDto<?>) node.getData();
            return key + ": " + dto.toString(FormatStyle.SIMPLE);
        }

        public static final SimpleDtoFormatter INSTANCE = new SimpleDtoFormatter();

    }

}
