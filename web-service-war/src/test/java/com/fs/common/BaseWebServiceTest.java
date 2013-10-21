package com.fs.common;

import org.junit.After;

public abstract class BaseWebServiceTest {

    protected APIHelper apiHelper;

    protected BaseWebServiceTest() {
        apiHelper = new APIHelper("localhost", 8080);
    }

    @After
    public void tearDowm() {
        apiHelper.closeHttpClient();
    }

}
