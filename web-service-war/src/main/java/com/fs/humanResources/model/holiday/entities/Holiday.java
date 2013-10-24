package com.fs.humanResources.model.holiday.entities;

import com.fs.humanResources.model.common.entities.BaseEntity;
import com.fs.humanResources.model.employee.entities.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Holiday extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Date startDate;

    private Date endDate;

    @ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
