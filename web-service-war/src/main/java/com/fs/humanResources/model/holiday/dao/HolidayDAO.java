package com.fs.humanResources.model.holiday.dao;

import com.fs.humanResources.common.exception.SaveHolidayException;
import com.fs.humanResources.model.holiday.entities.Holiday;

public interface HolidayDAO {

    void addHoliday(Holiday holiday) throws SaveHolidayException;
}
