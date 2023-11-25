package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class NasaBuilder {
    public static String urlImage(String urlNASA) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            ObjectMapper mapper = new ObjectMapper();
            CloseableHttpResponse response = httpClient.execute(new HttpGet(urlNASA));
            NASAObject nasaObject = null;
            nasaObject = mapper.readValue(response.getEntity().getContent(), NASAObject.class);
            String url = nasaObject.getUrl();
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
