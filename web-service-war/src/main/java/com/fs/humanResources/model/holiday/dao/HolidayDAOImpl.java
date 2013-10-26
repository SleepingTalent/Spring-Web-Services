package com.fs.humanResources.model.holiday.dao;

import com.fs.humanResources.common.exception.*;
import com.fs.humanResources.model.common.dao.BaseDAOImpl;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HolidayDAOImpl extends BaseDAOImpl<Holiday, Long> implements HolidayDAO {

    Logger log = Logger.getLogger(HolidayDAOImpl.class);

    @Autowired
    public HolidayDAOImpl(SessionFactory sessionFactory) {
        super(Holiday.class, sessionFactory);
    }

    @Override
    public void addHoliday(Holiday holiday) throws SaveHolidayException {
        try {
            saveOrUpdate(holiday);
        } catch (SaveEntityException see) {
            log.error("No Holidays for Employee with Id :"+holiday.getEmployee().getId(),see);
            throw new SaveHolidayException();
        }
    }

    @Override
    public Holiday findHoliday(Long holidayId) throws HolidayNotFoundException {
        Holiday holiday = findById(holidayId);

        if (holiday == null) {
            log.error("No Holidays found with Id :"+holidayId);
            throw new HolidayNotFoundException();
        }

        return holiday;
    }

    @Override
    public void deleteHoliday(Holiday holiday) throws DeleteHolidayException {
        try {
            delete(holiday);
        } catch (DeleteEntityException dee) {
            log.error("Error deleting Holiday with Id :"+holiday.getId(),dee);
            throw new DeleteHolidayException();
        }
    }

    @Override
    public List<Holiday> findHolidays(Employee employee) throws HolidayNotFoundException {
        List<Holiday> results = findByCriteria(Restrictions.eq("employee", employee));

        if (results == null && results.isEmpty()) {
            log.error("No Holidays for Employee with Id :"+employee.getId());
            throw new HolidayNotFoundException();
        }

        return results;
    }
}
