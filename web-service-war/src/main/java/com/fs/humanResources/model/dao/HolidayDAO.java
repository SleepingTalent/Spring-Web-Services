package com.fs.humanResources.model.dao;

import java.util.Date;

public interface HolidayDAO {

    public void addHoliday(Date startDate, Date endDate, Long employeeId);
}
