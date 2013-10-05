package com.fs.humanResources.domain;

public enum HolidayResponse {

    SUCCESS("Success"),
    FAILURE("Failure");

    private String status;

    HolidayResponse(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HolidayResponse{status='" + status + "'}";
    }
}
