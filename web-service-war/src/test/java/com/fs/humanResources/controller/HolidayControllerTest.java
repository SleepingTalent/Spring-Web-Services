package com.fs.humanResources.controller;

import com.fs.common.BaseUnitTest;
import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import com.fs.humanResources.service.HumanResourceService;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.*;

public class HolidayControllerTest extends BaseUnitTest {

    @InjectMocks
    HolidayController holidayController;

    @Mock
    HumanResourceService humanResourceService;

    HolidayRequest holidayRequest;

    @Before
    public void setUp() {
    }

    @Test
    public void addHoliday_returnsFailureResponse_whenAddRequestFails() throws HolidayRequestException {
        doThrow(new HolidayRequestException()).when(humanResourceService).
                bookHoliday(Matchers.<Date>anyObject(),Matchers.<Date>anyObject(),anyString());

       String response = holidayController.addHoliday(holidayRequest);
       Assert.assertEquals(HolidayResponse.FAILURE.toString(),response);
    }

    @Test
    public void addHoliday_returnsSuccessResponse_whenAddRequestSuceeds() throws HolidayRequestException {
        String response = holidayController.addHoliday(holidayRequest);

        verify(humanResourceService, times(1)).bookHoliday(Matchers.<Date>anyObject(),Matchers.<Date>anyObject(),anyString());
        Assert.assertEquals(HolidayResponse.SUCCESS.toString(), response);
    }
}
