package com.fs.humanResources.service;

import com.fs.humanResources.common.exception.HolidayRequestException;

import java.util.Date;

public interface HumanResourceService {
    public void bookHoliday(Date startDate, Date endDate, String name) throws HolidayRequestException;
}
