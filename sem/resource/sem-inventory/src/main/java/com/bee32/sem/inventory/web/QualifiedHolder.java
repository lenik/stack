package com.bee32.sem.inventory.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 不合格率查询输助类
 *
 */
public class QualifiedHolder implements Serializable, Comparable<QualifiedHolder> {

    private static final long serialVersionUID = 1L;

    Date date;
    BigDecimal qualified;
    BigDecimal unqualified;
    BigDecimal total;
    double unqualifiedRatio;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getQualified() {
		return qualified;
	}

	public void setQualified(BigDecimal qualified) {
		this.qualified = qualified;
	}

	public BigDecimal getUnqualified() {
		return unqualified;
	}

	public void setUnqualified(BigDecimal unqualified) {
		this.unqualified = unqualified;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public double getUnqualifiedRatio() {
	return unqualifiedRatio;
    }

	public void setUnqualifiedRatio(double unqualifiedRatio) {
	this.unqualifiedRatio = unqualifiedRatio;
    }

	@Override
    public int compareTo(QualifiedHolder o) {
	    if(date.before(o.getDate()))
		return -1;
	    else if(date.equals(o.getDate()))
		return 0;
	    else return 1;
    }
}
