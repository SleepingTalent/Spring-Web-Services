package com.fs.humanResources.service;

import com.fs.common.BaseUnitTest;
import com.fs.humanResources.model.employee.exception.EmployeeNotFoundException;
import com.fs.humanResources.model.holiday.exception.SaveHolidayException;
import com.fs.humanResources.model.employee.dao.EmployeeDAO;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.dao.HolidayDAO;
import com.fs.humanResources.model.holiday.entities.Holiday;
import com.fs.humanResources.service.exception.HolidayRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

public class HumanResourceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    HumanResourceServiceImpl humanResourceService;

    @Mock
    HolidayDAO holidayDAO;

    @Mock
    EmployeeDAO employeeDAO;

    SimpleDateFormat sdf;
    Date startOfMonth;
    Date endOfMonth;

    Employee employee;

    @Before
    public void setUp() throws ParseException {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startOfMonth = sdf.parse("2009-12-01");
        endOfMonth = sdf.parse("2009-12-31");

        employee = new Employee();
        employee.setId(1234l);
    }

    @Test
    public void bookHoliday_addsHolidayAsExpected() throws HolidayRequestException, EmployeeNotFoundException, SaveHolidayException {
        when(employeeDAO.findEmployee(anyLong())).thenReturn(employee);

        ArgumentCaptor<Holiday> holidayCaptor = ArgumentCaptor.forClass(Holiday.class);

        humanResourceService.bookHoliday(startOfMonth, endOfMonth, employee.getId());
        verify(holidayDAO, times(1)).addHoliday(holidayCaptor.capture());

        Assert.assertEquals(employee.getId(), holidayCaptor.getValue().getEmployee().getId());
        Assert.assertEquals(startOfMonth, holidayCaptor.getValue().getStartDate());
        Assert.assertEquals(endOfMonth, holidayCaptor.getValue().getEndDate());

    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenStartDateIsNull() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(null, new Date(), employee.getId());
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateIsNull() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(new Date(), null, employee.getId());
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateBeforeStartDate() throws HolidayRequestException {
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, employee.getId());
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsEmployeeNotFoundException_whenEmployeeIdNotFound() throws HolidayRequestException, EmployeeNotFoundException {
        when(employeeDAO.findEmployee(anyLong())).thenThrow(new EmployeeNotFoundException(""));
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, 1345l);
    }


    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsEmployeeNotFoundException_whenHolidaydNotAdded() throws HolidayRequestException, EmployeeNotFoundException, SaveHolidayException {
        doThrow(new SaveHolidayException("")).when(holidayDAO).addHoliday(Matchers.<Holiday>anyObject());
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, 1345l);
    }
}
