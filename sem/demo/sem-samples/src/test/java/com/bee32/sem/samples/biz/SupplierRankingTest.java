package com.bee32.sem.samples.biz;

import org.junit.Test;

import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.samples.user.Roles;
import com.bee32.sem.samples.user.User;
import com.bee32.sem.samples.user.UserGroup;
import com.bee32.sem.samples.user.UserGroups;

public class SupplierRankingTest {

    static UserGroup 小卖部 = new UserGroup("小卖部");

    static User WANG = new User("小王", UserGroups.卫生部, Roles.检疫科);
    static User LI = new User("小李", UserGroups.无线电协会, Roles.会员);
    static User ZHAO = new User("赵大", UserGroups._3G管理委员会, Roles._3G顾问);
    static User QIAN = new User("钱老", 小卖部, Roles.经理);

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb7byv-93s61n
     */

    @Test
    public void testFruit()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("fruit", "Example Company", 10);
        ranking.addVerifyPerson(WANG);
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test(expected = VerifyException.class)
    public void testFruitFail()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("fruit", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        ranking.getVerifyPolicy().verify(ranking);
    }

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb7gix-79ubco
     */

    @Test
    public void testPhone()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        ranking.addVerifyPerson(ZHAO); // OPTIONAL
        ranking.addVerifyPerson(QIAN);
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test
    public void testPhoneOptional()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        // ranking.addVerifyPerson(ZHAO); // OPTIONAL
        ranking.addVerifyPerson(QIAN);
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test
    public void testPhoneExtraInside()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        ranking.addVerifyPerson(WANG);
        ranking.addVerifyPerson(ZHAO);
        ranking.addVerifyPerson(QIAN);
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test
    public void testPhoneExtraAtEnd()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        ranking.addVerifyPerson(ZHAO);
        ranking.addVerifyPerson(QIAN);
        ranking.addVerifyPerson(WANG);// EXTRA
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test(expected = VerifyException.class)
    public void testPhoneFail()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(LI);
        ranking.addVerifyPerson(ZHAO);
        ranking.getVerifyPolicy().verify(ranking);
    }

    @Test(expected = VerifyException.class)
    public void testPhoneFailMisOrder()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("phone", "Example Company", 10);
        ranking.addVerifyPerson(QIAN);
        ranking.addVerifyPerson(ZHAO);
        ranking.addVerifyPerson(LI);
        ranking.getVerifyPolicy().verify(ranking);
    }

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb7e81jszhub
     */
    @Test
    public void testOther()
            throws Throwable {
        SupplierRanking ranking = new SupplierRanking("other", "Example Company", 10);
        ranking.getVerifyPolicy().verify(ranking);
    }

}
