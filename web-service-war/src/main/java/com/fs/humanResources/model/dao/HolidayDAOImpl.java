package com.fs.humanResources.model.dao;

import com.fs.humanResources.domain.HolidayRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class HolidayDAOImpl implements HolidayDAO{

    Logger log = Logger.getLogger(HolidayDAOImpl.class);

    @Override
    public void addHoliday(Date startDate, Date endDate, Long employeeId) {
        log.info("Booking holiday for ["+startDate+"-"+endDate+"] for ["+employeeId+"]");
    }
}
