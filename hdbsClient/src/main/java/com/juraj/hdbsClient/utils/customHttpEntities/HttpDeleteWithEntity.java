package com.juraj.hdbsClient.utils.customHttpEntities;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Juraj on 24.4.2018..
 */
public class HttpDeleteWithEntity extends HttpEntityEnclosingRequestBase {
    public final static String METHOD_NAME = "DELETE";

    public HttpDeleteWithEntity(String uri) {
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
