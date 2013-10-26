package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.HolidayNotValidException;
import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.common.exception.SaveHolidayException;
import com.fs.humanResources.model.employee.dao.EmployeeDAO;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.dao.HolidayDAO;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
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
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException {
        try {
            log.info("Booking Holiday for :" + employeeId);
            Employee employee = employeeDAO.findEmployee(employeeId);

            if (isBookingValid(startDate, endDate)) {
                Holiday holiday = new Holiday();
                holiday.setEmployee(employee);
                holiday.setStartDate(startDate);
                holiday.setEndDate(endDate);

                holidayDAO.addHoliday(holiday);
            } else {
                log.error("Holiday Booking is not Valid");
                throw new HolidayNotValidException("Holiday Booking is not Valid");
            }

        } catch (EmployeeNotFoundException | HolidayNotValidException | SaveHolidayException | Exception e) {
            log.error(e.getMessage(),e);
            throw new HolidayRequestException(e.getMessage());
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
