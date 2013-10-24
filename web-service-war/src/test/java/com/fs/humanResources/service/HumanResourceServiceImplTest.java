package com.fs.humanResources.service;

import com.fs.common.BaseUnitTest;
import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.model.holiday.dao.HolidayDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

public class HumanResourceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    HumanResourceServiceImpl humanResourceService;

    @Mock
    HolidayDAO holidayDAO;

    SimpleDateFormat sdf;
    Date startOfMonth;
    Date endOfMonth;

    @Before
    public void setUp() throws ParseException {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startOfMonth = sdf.parse("2009-12-01");
        endOfMonth = sdf.parse("2009-12-31");
    }

    @Test
    public void bookHoliday_addsHolidayAsExpected() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(startOfMonth,endOfMonth, 1345l);
        verify(holidayDAO, times(1)).addHoliday(eq(startOfMonth),eq(endOfMonth),eq(1345l));
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenStartDateIsNull() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(null,new Date(), 1345l);
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateIsNull() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(new Date(), null, 1345l);
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateBeforeStartDate() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, 1345l);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void bookHoliday_throwsEmployeeNotFoundException_whenEmployeeIdNotFound() throws HolidayRequestException, EmployeeNotFoundException {
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, 1345l);
    }
}
