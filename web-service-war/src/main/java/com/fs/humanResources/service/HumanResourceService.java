package com.fs.humanResources.service;

import com.fs.humanResources.service.exception.HolidayRequestException;

import java.util.Date;

public interface HumanResourceService {
    public void bookHoliday(Date startDate, Date endDate, Long employeeId) throws HolidayRequestException;
}
