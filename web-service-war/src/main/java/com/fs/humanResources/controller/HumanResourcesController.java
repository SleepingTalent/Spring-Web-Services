package com.fs.humanResources.controller;

import com.fs.humanResources.common.exception.EmployeeNotFoundException;
import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.common.exception.SaveHolidayException;
import com.fs.humanResources.domain.ResponseHelper;
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
public class HumanResourcesController {

    Logger log = Logger.getLogger(HumanResourcesController.class);

    HumanResourceService humanResourceService;

    @Autowired
    public HumanResourcesController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @RequestMapping(value = "addHoliday", method = RequestMethod.POST)
    @ResponseBody
    public HolidayResponse addHoliday(@RequestBody HolidayRequest holidayRequest) {
        try {
            log.info("Holiday Request Received!");
            humanResourceService.bookHoliday(holidayRequest.getStartDate(), holidayRequest.getEndDate(), holidayRequest.getEmployeeId());
            log.info("Holiday Added Successfully");
            return ResponseHelper.createHolidayResponse("Success", "Holiday Added Successfully", holidayRequest);
        } catch (HolidayRequestException hre) {
            log.error("Holiday Request Error",hre);
            return ResponseHelper.createHolidayResponse("Failure", hre.getMessage(),holidayRequest);
        }
    }
}
