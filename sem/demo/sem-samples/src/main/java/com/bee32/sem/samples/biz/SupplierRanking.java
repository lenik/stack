package com.bee32.sem.samples.biz;

import java.util.ArrayList;
import java.util.List;

import com.bee32.sem.process.verify.AbstractVerifyPolicy;
import com.bee32.sem.process.verify.BadVerifyDataException;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.impl.PassLog;
import com.bee32.sem.process.verify.impl.PassToNext;
import com.bee32.sem.process.verify.impl.PassToNext.VerifyStep;
import com.bee32.sem.samples.user.User;
import com.bee32.sem.samples.user.UserGroups;
import com.bee32.sem.samples.user.Roles;

/**
 * 供应商评级。
 *
 * 供应商
 */
public class SupplierRanking
        implements IVerifiable {

    /**
     * 主题产品类别
     */
    private String productClass;

    /**
     * 供应商名称
     */
    private String supplier;

    /**
     * 请求级别
     */
    private int requestRank;

    private PassLog verifyData;

    public SupplierRanking(String productClass, String supplier, int requestRank) {
        if (productClass == null)
            throw new NullPointerException("productClass");
        if (supplier == null)
            throw new NullPointerException("supplier");
        this.productClass = productClass;
        this.supplier = supplier;
        this.requestRank = requestRank;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        if (productClass == null)
            throw new NullPointerException("productClass");
        this.productClass = productClass;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        if (supplier == null)
            throw new NullPointerException("supplier");
        this.supplier = supplier;
    }

    public int getRequestRank() {
        return requestRank;
    }

    public void setRequestRank(int requestRank) {
        this.requestRank = requestRank;
    }

    @Override
    public IVerifyPolicy getVerifyPolicy() {
        if ("fruit".equals(productClass)) {
            return fruitStrategy;
        } else if ("phone".equals(productClass)) {
            return phoneStrategy;
        } else {
            // 其它类别不要求审核
            return new AbstractVerifyPolicy() {
                @Override
                public void verify(IVerifiable verifiableObject)
                        throws VerifyException, BadVerifyDataException {
                    // 默认总是通过
                }
            };
        }
    }

    @Override
    public PassLog getVerifyState() {
        return verifyData;
    }

    public void setVerifyData(PassLog verifyData) {
        this.verifyData = verifyData;
    }

    public void addVerifyPerson(User person) {
        if (verifyData == null)
            verifyData = new PassLog();
        verifyData.passBy(person);
    }

    private static PassToNext fruitStrategy;
    private static PassToNext phoneStrategy;
    static {
        // 水果供应商评级只需卫生部审核即可。
        List<VerifyStep> fruitSteps = new ArrayList<VerifyStep>();
        fruitSteps.add(new VerifyStep(UserGroups.卫生部, null, false));
        fruitStrategy = new PassToNext(fruitSteps);

        // 手机供应商评级
        List<VerifyStep> phoneSteps = new ArrayList<VerifyStep>();
        phoneSteps.add(new VerifyStep(UserGroups.无线电协会, null, false));
        phoneSteps.add(new VerifyStep(UserGroups._3G管理委员会, null, true));
        phoneSteps.add(new VerifyStep(null, Roles.经理, false));
        phoneStrategy = new PassToNext(phoneSteps);
    }

}
