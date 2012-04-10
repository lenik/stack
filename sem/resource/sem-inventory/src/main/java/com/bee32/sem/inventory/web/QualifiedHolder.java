package com.bee32.sem.inventory.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 不合格率查询输助类
 *
 */
public class QualifiedHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    Date date;
    BigDecimal qualified;
    BigDecimal unqualified;
    BigDecimal total;
    BigDecimal unqualifiedRatio;
}
