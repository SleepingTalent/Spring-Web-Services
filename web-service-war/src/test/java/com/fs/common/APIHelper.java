package com.fs.common;


import com.fs.humanResources.domain.HolidayRequest;
import com.fs.humanResources.domain.HolidayResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
import java.io.*;
import java.util.Date;

public class APIHelper {

    Logger log = Logger.getLogger(APIHelper.class);

    private static final String API_PATH = "humanResources-services/api/";
    private static final String APPLICATION_XML_TYPE = "application/xml";

    private String host;
    private int port;

    private DefaultHttpClient httpClient;

    public APIHelper(String host, int port) {
        this.host = host;
        this.port = port;
        this.httpClient = new DefaultHttpClient();
    }

    public <T> Object unmarshalEntityToObject(HttpEntity entity, Class<T> classType) throws IOException, JAXBException {
        String xmlString = EntityUtils.toString(entity);
        ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes());
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(input));
    }

    public <T> OutputStream marshalObjectToStream(T object, Class<T> classType) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(classType);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        OutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, outputStream);
        return outputStream;
    }

    public OutputStream marshalHolidayRequest(HolidayRequest holidayRequest) throws JAXBException {
        return marshalObjectToStream(holidayRequest, HolidayRequest.class);
    }

    public HolidayResponse unmarshalHolidayResponse(HttpEntity entity) throws JAXBException, IOException {
        return (HolidayResponse) unmarshalEntityToObject(entity, HolidayResponse.class);
    }

    public HttpResponse sendPostRequest(String apiurl, OutputStream postBody) {
        HttpResponse httpResponse = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            String url = getfullAPIUrl(apiurl);
            log.info("Posting to URL :" + url);

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("content-type", APPLICATION_XML_TYPE);

            StringEntity entity = new StringEntity(postBody.toString());
            httpPost.setEntity(entity);

            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            log.error(e);
        } finally {
            return httpResponse;
        }
    }

    private String getfullAPIUrl(String apiurl) {
        return "http://" + host + ":" + port + "/" + API_PATH + apiurl;
    }

    public void closeHttpClient() {
        if (httpClient != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public HolidayRequest createHolidayRequest(long employeeId, Date startDate, Date endDate) {
        HolidayRequest holidayRequest = new HolidayRequest();
        holidayRequest.setEmployeeId(employeeId);
        holidayRequest.setStartDate(startDate);
        holidayRequest.setEndDate(endDate);
        return holidayRequest;
    }

}
