package com.fs.common;


import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class APIHelper {

    private static final String API_PATH = "humanResources-services/api/";

    private String host;
    private int port;

    public APIHelper(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getfullAPIUrl(String apiurl) {
        return "http://" + host + ":" + port + "/" + API_PATH + apiurl;
    }

    public HolidayRequest createHolidayRequest(long employeeId, Date startDate, Date endDate) {
        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setEmployeeId(employeeId);
        holidayRequest.setStartDate(startDate);
        holidayRequest.setEndDate(endDate);
        return holidayRequest;
    }
}
