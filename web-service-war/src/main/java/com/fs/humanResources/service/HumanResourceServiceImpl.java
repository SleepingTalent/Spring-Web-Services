package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.HolidayRequestException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {

    @Override
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException {

        if (startDate == null) {
            throw new HolidayRequestException();
        } else if (endDate == null) {
            throw new HolidayRequestException();
        } else {
            System.out.println("Booking holiday for [" +
                    startDate + "-" + endDate + "] for [" + employeeId + "] ");
        }
    }
}
