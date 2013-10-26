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
            throw new SaveHolidayException("No Holidays for Employee with Id :"+holiday.getEmployee().getId());
        }
    }

}
