package com.fs.humanResources.service;

import com.fs.common.BaseUnitTest;
import com.fs.humanResources.common.exception.HolidayRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HumanResourceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    HumanResourceServiceImpl humanResourceService;

    SimpleDateFormat sdf;
    Date startOfMonth;
    Date endOfMonth;

    @Before
    public void setUp() throws ParseException {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        startOfMonth = sdf.parse("2009-12-01");
        endOfMonth = sdf.parse("2009-12-31");
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenStartDateIsNull() throws HolidayRequestException {
        humanResourceService.bookHoliday(null,new Date(), 1345l);
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateIsNull() throws HolidayRequestException {
        humanResourceService.bookHoliday(new Date(), null, 1345l);
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateBeforeStartDate() throws HolidayRequestException {
        humanResourceService.bookHoliday(endOfMonth, startOfMonth, 1345l);
    }
}
