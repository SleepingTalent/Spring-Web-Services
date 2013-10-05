package com.fs.integration.humanResources;

import com.fs.common.BaseSeleniumTest;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class HumanResourcesAPIIntegrationTest extends BaseSeleniumTest {

    Logger log = Logger.getLogger(HumanResourcesAPIIntegrationTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void ping_returnsAsExpected() {
       callUrl("http://localhost:8181/humanResources-services/api/ping");
       Assert.assertEquals("HolidayResponse{status='Success'}",getDriver().getPageSource());
    }


}