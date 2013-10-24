package com.fs.humanResources.model.employee.entities;

import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.common.entities.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Employee extends BaseEntity implements Comparable<Employee> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    @OneToMany(mappedBy="employee",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addressList = new ArrayList();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddressList(List<Address> address) {
        this.addressList = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void addAddress(Address address) {
        addressList.add(address);
        if(address.getEmployee() != this) {
           address.setEmployee(this);
        }
    }

    public int compareTo(Employee employee) {
        if (this.id < employee.id) {
            return -1;
        }else if (this.id > employee.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
