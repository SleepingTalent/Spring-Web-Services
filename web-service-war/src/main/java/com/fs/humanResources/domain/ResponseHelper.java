package com.fs.humanResources.domain;

public class ResponseHelper {

    public static HolidayResponse createHolidayResponse(String status, String message, HolidayRequest holidayRequest) {
        HolidayResponse holidayResponse = new HolidayResponse();
        holidayResponse.setEmployeeId(holidayRequest.getEmployeeId());
        holidayResponse.setStartDate(holidayRequest.getStartDate());
        holidayResponse.setEndDate(holidayRequest.getEndDate());
        holidayResponse.setStatus(status);
        holidayResponse.setMessage(message);
        return holidayResponse;
    }
}
