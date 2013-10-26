package com.fs.humanResources.model.holiday.dao;

import com.fs.humanResources.common.exception.DeleteHolidayException;
import com.fs.humanResources.common.exception.HolidayNotFoundException;
import com.fs.humanResources.common.exception.SaveHolidayException;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.entities.Holiday;

import java.util.List;

public interface HolidayDAO {

    void addHoliday(Holiday holiday) throws SaveHolidayException;
    Holiday findHoliday(Long holidayId) throws HolidayNotFoundException;
    void deleteHoliday(Holiday holiday) throws DeleteHolidayException;

    List<Holiday> findHolidays(Employee employee) throws HolidayNotFoundException;
}
