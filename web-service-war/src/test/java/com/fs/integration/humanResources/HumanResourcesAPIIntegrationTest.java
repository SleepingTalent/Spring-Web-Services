package com.fs.integration.humanResources;

import com.fs.common.BaseSeleniumTest;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.junit.Assert.fail;

public class HumanResourcesAPIIntegrationTest extends BaseSeleniumTest {

    Logger log = Logger.getLogger(HumanResourcesAPIIntegrationTest.class);

    @Before
    public void setUp() {
        apiHelper.setHostAndPort("localhost", 8181);
    }

    @Test
    public void holidayResponse_returnsAsExpected() throws JAXBException, IOException {
        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setEmployeeId(1234l);
        holidayRequest.setStartDate(new Date());
        holidayRequest.setEndDate(new Date());

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);
        Assert.assertEquals(200,response.getStatusLine().getStatusCode());
        log.info("Response As String "+ response.toString());

        HttpEntity httpEntity = response.getEntity();
        Assert.assertNotNull(httpEntity);

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(httpEntity);
        log.info(actualHolidayResponse.toString());
    }


}