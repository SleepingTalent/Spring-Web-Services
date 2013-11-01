package com.fs.common;

import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.employee.entities.Employee;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ContextConfiguration(locations = "/test-context.xml")
public abstract class BaseWebServiceTest {

    @Autowired
    protected RestTemplate restTemplate;

    protected APIHelper apiHelper;

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected BaseWebServiceTest() {
        apiHelper = new APIHelper("localhost", 8580);
    }

    protected Employee createEmployee() throws ParseException {
        Address address = new Address();
        address.setHouseNumber("50");
        address.setAddressFirstLine(PersistenceHelper.getUniqueString(8));
        address.setAddressSecondLine("Domain Court");
        address.setTownCity("Progammer City");
        address.setPostCode("AB1CDX");
        address.setPrimaryAddress(true);

        Employee employee = new Employee();
        employee.setFirstName("James");
        employee.setLastName(PersistenceHelper.getUniqueString(8));

        employee.setDateOfBirth(sdf.parse("1976-07-15"));

        employee.addAddress(address);
        return employee;
    }

}
