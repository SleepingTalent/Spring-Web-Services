package com.fs.humanResources.controller;

import com.fs.humanResources.common.exception.HolidayRequestException;
import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import com.fs.humanResources.service.HumanResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class HolidayController {

    HumanResourceService humanResourceService;

    @Autowired
    public HolidayController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @RequestMapping(value = "addHoliday", method = RequestMethod.POST)
    @ResponseBody
    public String addHoliday(HolidayRequest holidayRequest) {
        try {
            humanResourceService.bookHoliday(
                    holidayRequest.getStartDate(), holidayRequest.getEndDate(), holidayRequest.getEmployeeId());
            return HolidayResponse.SUCCESS.toString();
        } catch (HolidayRequestException hre) {
            return HolidayResponse.FAILURE.toString();
        }
    }
}
