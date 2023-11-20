package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static final String url = "https://api.nasa.gov/planetary/apod?api_key=ojmVIFTUdRRm8kDaqPcHZKBEGVwsgIn6IaLOFt9B";
    public static final String path = "ASAImage/";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        NASAObject nasaObject = mapper.readValue(response.getEntity().getContent(), NASAObject.class);
        CloseableHttpResponse imageResponse = httpClient.execute(new HttpGet(nasaObject.getUrl()));
        String[] arr = nasaObject.getUrl().split("/");
        String fileName = arr[arr.length - 1];
        HttpEntity entity = imageResponse.getEntity();
        FileOutputStream fos = new FileOutputStream(path + fileName);
        entity.writeTo(fos);
        fos.close();
    }
}