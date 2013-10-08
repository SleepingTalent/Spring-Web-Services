package com.fs.integration.humanResources;

import com.fs.common.BaseWebServiceTest;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static org.junit.Assert.fail;

public class HumanResourcesAPIIntegrationTest extends BaseWebServiceTest {

    Logger log = Logger.getLogger(HumanResourcesAPIIntegrationTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void addHoliday_holidayResponseReturnWithSuccessStatus_whenHolidaySuccessfullyAdded() throws JAXBException, IOException {
        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setEmployeeId(1234l);
        holidayRequest.setStartDate(new Date());
        holidayRequest.setEndDate(new Date());

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        Assert.assertNotNull("HttpResponse is Null!", response);
        Assert.assertNotNull("HttpResponse StatusLine is Null!", response.getStatusLine());

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200,response.getStatusLine().getStatusCode());

        HttpEntity httpEntity = response.getEntity();
        Assert.assertNotNull("HttpEntity is Null!",httpEntity);

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(httpEntity);

        log.info(actualHolidayResponse.toString());
        Assert.assertEquals("Success",actualHolidayResponse.getStatus());
    }
}