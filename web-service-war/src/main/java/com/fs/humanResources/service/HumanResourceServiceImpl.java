package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.HolidayRequestException;
import org.springframework.stereotype.Service;

import java.util.Date;
import org.apache.log4j.Logger;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {

    Logger log = Logger.getLogger(HumanResourceServiceImpl.class);

    @Override
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException {

        if (isBookingValid(startDate, endDate)) {
            log.info("Booking holiday for ["+startDate+"-"+endDate+"] for ["+employeeId+"]");
        } else {
            throw new HolidayRequestException();
        }
    }

    private boolean isBookingValid(Date startDate, Date endDate) {
        if ((startDate == null) || (endDate == null) || (startDate.after(endDate))) {
           return false;
        }

        return true;
    }
}
