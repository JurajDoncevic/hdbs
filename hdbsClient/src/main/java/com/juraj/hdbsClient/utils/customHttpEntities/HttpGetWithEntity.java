package com.juraj.hdbsClient.utils.customHttpEntities;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
    public final static String METHOD_NAME = "GET";

    public HttpGetWithEntity(String uri) {
        try {
            setURI(new URI(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}