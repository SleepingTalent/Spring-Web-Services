package com.fs.humanResources.service;

import com.fs.common.BaseUnitTest;
import com.fs.humanResources.common.exception.HolidayRequestException;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Date;

public class HumanResourceServiceImplTest extends BaseUnitTest {

    @InjectMocks
    HumanResourceServiceImpl humanResourceService;

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenStartDateIsNull() throws HolidayRequestException {
        humanResourceService.bookHoliday(null,new Date(), 1345l);
    }

    @Test(expected = HolidayRequestException.class)
    public void bookHoliday_throwsHolidayRequestException_whenEndDateIsNull() throws HolidayRequestException {
        humanResourceService.bookHoliday(new Date(), null, 1345l);
    }
}
