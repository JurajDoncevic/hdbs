package com.juraj.hdbsWebAPI.models;

/**
 * Created by Juraj on 18.4.2018..
 */
public class QueryResponse {

    private String dataResult;
    private String message;

    public QueryResponse(String dataResult, String message) {
        this.dataResult = dataResult;
        this.message = message;
    }

    public String getDataResult() {
        return dataResult;
    }

    public String getMessage() {
        return message;
    }
}
