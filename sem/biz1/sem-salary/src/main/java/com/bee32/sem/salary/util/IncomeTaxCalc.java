package com.bee32.sem.salary.util;

import java.math.BigDecimal;

import javax.free.Pair;

public class IncomeTaxCalc {

    static BigDecimal start = new BigDecimal(3500);
    static Pair<BigDecimal, BigDecimal> level1 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(1500), new BigDecimal(0.03));
    static Pair<BigDecimal, BigDecimal> level2 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(3000), new BigDecimal(0.1));
    static Pair<BigDecimal, BigDecimal> level3 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(4500), new BigDecimal(0.2));
    static Pair<BigDecimal, BigDecimal> level4 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(26000),new BigDecimal(0.25));
    static Pair<BigDecimal, BigDecimal> level5 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(20000),new BigDecimal(0.3));
    static Pair<BigDecimal, BigDecimal> level6 = new Pair<BigDecimal, BigDecimal>(new BigDecimal(25000),new BigDecimal(0.35));
    static Pair<BigDecimal, BigDecimal> level7 = new Pair<BigDecimal, BigDecimal>(BigDecimal.ZERO, new BigDecimal(0.45));

    /**
     * return Pair<tax, afterTax>
     */
    public static Pair<BigDecimal, BigDecimal> calcIncomeTax(BigDecimal total) {
        BigDecimal tax = getTax(total);
        return new Pair<BigDecimal, BigDecimal>(tax, total);
    }

// tax = tax.add(tax1);
// tax = tax.add(tax2);
// tax = tax.add(tax3);
// tax = tax.add(tax4);
// tax = tax.add(tax5);
// tax = tax.add(tax6);
    public static BigDecimal getTax(BigDecimal total) {
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal exceed = total.subtract(start);
        if (exceed.compareTo(level1.getFirst()) > 0) {
            tax = tax.add(level1.getFirst().multiply(level1.getSecond()));
            exceed = exceed.subtract(level1.getFirst());
            if (exceed.compareTo(level2.getFirst()) > 0) {
                tax = tax.add(level2.getFirst().multiply(level2.getSecond()));
                exceed = exceed.subtract(level2.getFirst());
                if (exceed.compareTo(level3.getFirst()) > 0) {
                    tax = tax.add(level3.getFirst().max(level3.getSecond()));
                    exceed = exceed.subtract(level3.getFirst());
                    if (exceed.compareTo(level4.getFirst()) > 0) {
                        tax = tax.add(level4.getFirst().multiply(level4.getSecond()));
                        exceed = exceed.subtract(level4.getFirst());
                        if (exceed.compareTo(level5.getFirst()) > 0) {
                            tax = tax.add(level5.getFirst().multiply(level5.getSecond()));
                            exceed = exceed.subtract(level5.getFirst());
                            if (exceed.compareTo(level6.getFirst()) > 0) {
                                tax = tax.add(level6.getFirst().multiply(level6.getSecond()));
                                exceed = exceed.subtract(level6.getFirst());
                                tax = tax.add(exceed.multiply(level7.getSecond()));
                            } else
                                tax = tax.add(exceed.multiply(level6.getSecond()));
                        } else
                            tax = tax.add(exceed.multiply(level5.getSecond()));
                    } else
                        tax = tax.add(exceed.multiply(level4.getSecond()));
                } else
                    tax = tax.add(exceed.multiply(level3.getSecond()));
            } else
                tax = tax.add(exceed.multiply(level2.getSecond()));
        } else
            tax = tax.add(exceed.multiply(level1.getSecond()));

        return tax;
    }
}
