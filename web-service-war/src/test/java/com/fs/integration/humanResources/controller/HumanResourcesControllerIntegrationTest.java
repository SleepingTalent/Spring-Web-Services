package com.fs.integration.humanResources.controller;

import com.fs.common.BaseWebServiceTest;
import com.fs.common.PersistenceHelper;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.common.exception.DeleteEntityException;
import com.fs.humanResources.model.common.exception.SaveEntityException;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.exception.HolidayNotFoundException;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HumanResourcesControllerIntegrationTest extends BaseWebServiceTest {

    Logger log = Logger.getLogger(HumanResourcesControllerIntegrationTest.class);

    Date startOfMonth;
    Date endOfMonth;

    Employee employee;

    boolean holidaySaved = false;

    @Before
    public void setUp() throws ParseException, SaveEntityException {
        startOfMonth = sdf.parse("2009-12-01");
        endOfMonth = sdf.parse("2009-12-31");

        employee = createEmployee();

        PersistenceHelper.saveEmployee(employee);
    }

    @After
    public void tearDown() throws HolidayNotFoundException, DeleteEntityException {
        if(holidaySaved) {
           List<Holiday> foundHolidays = PersistenceHelper.findHolidays(employee);
           for(Holiday holiday : foundHolidays) {
               PersistenceHelper.deleteHoliday(holiday);
           }
        }

        Employee foundEmployee = PersistenceHelper.findEmployee(employee.getId());
        PersistenceHelper.deleteEmployee(foundEmployee);
    }

    @Test
    public void addHoliday_holidayResponseReturnWithSuccessStatus_whenHolidaySuccessfullyAdded() throws JAXBException, IOException, HolidayNotFoundException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(employee.getId(), startOfMonth, endOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Success", actualHolidayResponse.getStatus());
        Assert.assertEquals("Holiday Added Successfully", actualHolidayResponse.getMessage());

        List<Holiday> foundHolidays = PersistenceHelper.findHolidays(employee);
        Assert.assertEquals(1,foundHolidays.size());
        Assert.assertEquals(employee.getId(),foundHolidays.get(0).getEmployee().getId());
        Assert.assertEquals(startOfMonth,foundHolidays.get(0).getStartDate());
        Assert.assertEquals(endOfMonth,foundHolidays.get(0).getEndDate());

        holidaySaved = true;
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenStartDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(employee.getId(), null, endOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
        Assert.assertEquals("Holiday Booking is not Valid", actualHolidayResponse.getMessage());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenEndDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(employee.getId(), startOfMonth, null);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
        Assert.assertEquals("Holiday Booking is not Valid", actualHolidayResponse.getMessage());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenStartDateIsAfterEndDate() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(employee.getId(), endOfMonth, startOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());

        Assert.assertEquals(holidayRequest.getEmployeeId(), actualHolidayResponse.getEmployeeId());
        Assert.assertEquals(holidayRequest.getStartDate(), actualHolidayResponse.getStartDate());
        Assert.assertEquals(holidayRequest.getEndDate(), actualHolidayResponse.getEndDate());
        Assert.assertEquals("Holiday Booking is not Valid", actualHolidayResponse.getMessage());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenEmployeeNotFound() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(9999, startOfMonth, endOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());

        Assert.assertEquals(holidayRequest.getEmployeeId(), actualHolidayResponse.getEmployeeId());
        Assert.assertEquals(holidayRequest.getStartDate(), actualHolidayResponse.getStartDate());
        Assert.assertEquals(holidayRequest.getEndDate(), actualHolidayResponse.getEndDate());
        Assert.assertEquals("Employee Not Found with Id : 9999", actualHolidayResponse.getMessage());
    }
}