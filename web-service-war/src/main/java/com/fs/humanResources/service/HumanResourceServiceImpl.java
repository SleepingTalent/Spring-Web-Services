package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.HolidayRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fs.humanResources.model.holiday.dao.HolidayDAO;

import java.util.Date;
import org.apache.log4j.Logger;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {

    Logger log = Logger.getLogger(HumanResourceServiceImpl.class);

    HolidayDAO holidayDAO;

    @Autowired
    public HumanResourceServiceImpl(HolidayDAO holidayDAO) {
        this.holidayDAO = holidayDAO;
    }

    @Override
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException {

        if (isBookingValid(startDate, endDate)) {
           holidayDAO.addHoliday(startDate, endDate, employeeId);
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
