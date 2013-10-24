package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.HolidayRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fs.humanResources.model.holiday.dao.HolidayDAO;
import com.fs.humanResources.model.employee.dao.EmployeeDAO;

import java.util.Date;
import org.apache.log4j.Logger;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {

    Logger log = Logger.getLogger(HumanResourceServiceImpl.class);

    HolidayDAO holidayDAO;

    EmployeeDAO employeeDAO;

    @Autowired
    public HumanResourceServiceImpl(HolidayDAO holidayDAO, EmployeeDAO employeeDAO) {
        this.holidayDAO = holidayDAO;
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException, EmployeeNotFoundException {

        log.info("Booking Holiday for :"+employeeId);
        employeeDAO.findEmployee(employeeId);

        if (isBookingValid(startDate, endDate)) {
           holidayDAO.addHoliday(startDate, endDate, employeeId);
        } else {
            throw new HolidayRequestException();
        }
    }

    private boolean isBookingValid(Date startDate, Date endDate) {
        if ((startDate == null) || (endDate == null) || (startDate.after(endDate))) {
           log.info("Booking is not valid!");
           return false;
        }

        log.info("Booking is invalid!");
        return true;
    }
}
