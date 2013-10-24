package com.fs.integration.humanResources;

import com.fs.common.BaseWebServiceTest;
import com.fs.common.PersistenceHelper;
import com.fs.humanResources.common.exception.DeleteEmployeeException;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.SaveEmployeeException;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.employee.dao.EmployeeDAO;
import com.fs.humanResources.model.employee.dao.EmployeeDAOImpl;
import com.fs.humanResources.model.employee.entities.Employee;
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

public class HumanResourcesAPIIntegrationTest extends BaseWebServiceTest {

    Logger log = Logger.getLogger(HumanResourcesAPIIntegrationTest.class);

    SimpleDateFormat sdf;
    Date startOfMonth;
    Date endOfMonth;

    Employee employee;

    EmployeeDAO employeeDAO;

    @Before
    public void setUp() throws ParseException, SaveEmployeeException {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startOfMonth = sdf.parse("2009-12-01");
        endOfMonth = sdf.parse("2009-12-31");

        Address address = new Address();
        address.setHouseNumber("50");
        address.setAddressFirstLine(PersistenceHelper.getUniqueString(8));
        address.setAddressSecondLine("Domain Court");
        address.setTownCity("Progammer City");
        address.setPostCode("AB1CDX");
        address.setPrimaryAddress(true);

        employee = new Employee();
        employee.setFirstName("James");
        employee.setLastName(PersistenceHelper.getUniqueString(8));

        employee.setDateOfBirth(sdf.parse("1976-07-15"));

        employee.addAddress(address);

        employeeDAO = new EmployeeDAOImpl(PersistenceHelper.getSessionFactory());
        employeeDAO.saveEmployee(employee);
    }

    @After
    public void tearDown() throws EmployeeNotFoundException, DeleteEmployeeException {
        Employee foundEmployee = employeeDAO.findEmployee(employee.getId());
        employeeDAO.deleteEmployee(foundEmployee);
    }

    @Test
    public void addHoliday_holidayResponseReturnWithSuccessStatus_whenHolidaySuccessfullyAdded() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l, startOfMonth, endOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Success", actualHolidayResponse.getStatus());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenStartDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l, null, endOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenEndDateIsNull() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l, startOfMonth, null);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());
    }

    @Test
    public void addHoliday_holidayResponseReturnWithStatus_whenStartDateIsAfterEndDate() throws JAXBException, IOException {
        HolidayRequest holidayRequest = apiHelper.createHolidayRequest(1234l, endOfMonth, startOfMonth);

        OutputStream postBody = apiHelper.marshalHolidayRequest(holidayRequest);
        HttpResponse response = apiHelper.sendPostRequest("addHoliday", postBody);

        log.info("Response Status Code " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HolidayResponse actualHolidayResponse = apiHelper.unmarshalHolidayResponse(response.getEntity());
        Assert.assertEquals("Failure", actualHolidayResponse.getStatus());

        Assert.assertEquals(holidayRequest.getEmployeeId(), actualHolidayResponse.getEmployeeId());
        Assert.assertEquals(holidayRequest.getStartDate(), actualHolidayResponse.getStartDate());
        Assert.assertEquals(holidayRequest.getEndDate(), actualHolidayResponse.getEndDate());
    }
}