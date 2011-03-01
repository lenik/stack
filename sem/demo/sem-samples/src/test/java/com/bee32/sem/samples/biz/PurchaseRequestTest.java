package com.bee32.sem.samples.biz;

import org.junit.Test;

import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.samples.user.Users;

public class PurchaseRequestTest {

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb0phd-3jlpmc
     */

    @Test
    public void testOk()
            throws Throwable {
        PurchaseRequest request = new PurchaseRequest();
        request.setVerifierPerson(Users.tom);
        request.getVerifyPolicy().verify(request);
    }

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb0mfk-yfhtbn
     */

    @Test(expected = VerifyException.class)
    public void testFail()
            throws Throwable {
        PurchaseRequest request = new PurchaseRequest();
        request.setVerifierPerson(Users.张三);
        request.getVerifyPolicy().verify(request);
    }

    /**
     * @url element://model:project::ebc-samples/design:view:::Vam8ynkg3bb0o0fdyxc2
     */

    @Test(expected = VerifyException.class)
    public void testNullFail()
            throws Throwable {
        PurchaseRequest request = new PurchaseRequest();
        request.setVerifierPerson(null);
        request.getVerifyPolicy().verify(request);
    }

}
