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

public class APIHelper {

    Logger log = Logger.getLogger(APIHelper.class);

    private String host = "myHost";
    private int port = 8080;
    private static final String PROTOCAL = "http";

    private static final String APPLICATION_XML_TYPE = "application/xml";
    private static final String API_PATH = "humanResources-services/api/";

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
        return (HolidayResponse)unmarshalEntityToObject(entity, HolidayResponse.class);
    }

    public HttpResponse sendPostRequest(String apiurl, OutputStream postBody) {
        HttpResponse httpResponse = null;
        HttpHost httpHost = new HttpHost(host, port, PROTOCAL);
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            log.info("Posting to URL :"+API_PATH+apiurl);
            HttpPost httpPost = new HttpPost(API_PATH+apiurl);
            StringEntity entity = new StringEntity(postBody.toString());
            entity.setContentType(APPLICATION_XML_TYPE);
            httpPost.setEntity(entity);

            httpResponse = httpClient.execute(httpHost, httpPost);
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        } catch (ClientProtocolException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        } finally {
            httpClient.getConnectionManager().shutdown();
            return httpResponse;
        }
    }

    public void setHostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
