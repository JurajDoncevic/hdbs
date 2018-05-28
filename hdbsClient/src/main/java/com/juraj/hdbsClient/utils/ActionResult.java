package com.juraj.hdbsClient.utils;

/**
 * Created by Juraj on 24.4.2018..
 */
public class ActionResult {
    private ActionResultType actionResultType;
    private String message;
    private String data;

    public ActionResult(ActionResultType actionResultType, String message) {
        this.actionResultType = actionResultType;
        this.message = message;
        this.data = "";
    }

    public ActionResult(ActionResultType actionResultType, String message, String data) {
        this.actionResultType = actionResultType;
        this.message = message;
        this.data = data;
    }

    public ActionResultType getActionResultType() {
        return this.actionResultType;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return this.actionResultType + ":" + this.message;
    }

    public String getData() {
        return this.data;
    }
}