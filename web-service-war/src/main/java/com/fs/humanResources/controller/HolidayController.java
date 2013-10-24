package com.fs.humanResources.controller;

import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import com.fs.humanResources.service.HumanResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class HolidayController {

    Logger log = Logger.getLogger(HolidayController.class);

    HumanResourceService humanResourceService;

    @Autowired
    public HolidayController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @RequestMapping(value = "addHoliday", method = RequestMethod.POST)
    @ResponseBody
    public HolidayResponse addHoliday(@RequestBody HolidayRequest holidayRequest) {
        try {
            log.info("Holiday Request Received!");
            humanResourceService.bookHoliday(holidayRequest.getStartDate(), holidayRequest.getEndDate(), holidayRequest.getEmployeeId());
            return createHolidayResponse("Success", holidayRequest);
        } catch (HolidayRequestException hre) {
            return createHolidayResponse("Failure", holidayRequest);
        } catch (EmployeeNotFoundException e) {
            return createHolidayResponse("Failure", holidayRequest);
        }
    }

    private HolidayResponse createHolidayResponse(String status, HolidayRequest holidayRequest) {
        HolidayResponse holidayResponse = new HolidayResponse();
        holidayResponse.setEmployeeId(holidayRequest.getEmployeeId());
        holidayResponse.setStartDate(holidayRequest.getStartDate());
        holidayResponse.setEndDate(holidayRequest.getEndDate());
        holidayResponse.setStatus(status);
        return holidayResponse;
    }
}
