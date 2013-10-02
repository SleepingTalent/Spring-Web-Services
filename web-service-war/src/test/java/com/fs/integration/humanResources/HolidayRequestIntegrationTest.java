package com.fs.integration.humanResources;

import com.fs.common.BaseSeleniumTest;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class HolidayRequestIntegrationTest extends BaseSeleniumTest {

    Logger log = Logger.getLogger(HolidayRequestIntegrationTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void wsdl_returnsAsExpected() {
       callUrl("http://localhost:8181//humanResources-services/holidayService/holiday.wsdl");
       log.info(getDriver().getPageSource());
    }


}