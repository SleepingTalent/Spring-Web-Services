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
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Success", actualHolidayResponse.getStatus());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenStartDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l,null,new Date());

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenEndDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l,new Date(),null);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
    }
}