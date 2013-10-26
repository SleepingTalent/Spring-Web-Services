package com.fs.humanResources.model.holiday.dao;

import com.fs.humanResources.model.holiday.entities.Holiday;
import com.fs.humanResources.model.holiday.exception.SaveHolidayException;

public interface HolidayDAO {

    void addHoliday(Holiday holiday) throws SaveHolidayException;
}
