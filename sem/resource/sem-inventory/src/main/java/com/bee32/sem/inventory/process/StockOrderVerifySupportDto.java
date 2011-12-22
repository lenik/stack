package com.bee32.sem.inventory.process;

import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;

public class StockOrderVerifySupportDto
        extends SingleVerifierWithNumberSupportDto {

    private static final long serialVersionUID = 1L;

    IStockSubjectAware stockSubjectAware;

    @Override
    protected void _marshal(SingleVerifierSupport _source) {
        super._marshal(_source);
    }

    @Override
    protected void _unmarshalTo(SingleVerifierSupport target) {
        super._unmarshalTo(target);
    }

}
