package com.fs.humanResources.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlRootElement(name = "HolidayResponse")
@XmlType(propOrder = {"employeeId","startDate","endDate","status", "message"})
public class HolidayResponse {

    Long employeeId;

    Date startDate;

    Date endDate;

    String status;

    String message;

    @XmlElement
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @XmlElement
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlElement
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "HolidayResponse{" +
                "employeeId=" + employeeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
