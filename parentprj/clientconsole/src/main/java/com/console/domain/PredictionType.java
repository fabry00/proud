package com.console.domain;

/**
 * Created by exfaff on 28/09/2016.
 */
public enum PredictionType {
    NOT_DETECTED(false,"NOT DETECTED"),
    DETECTED(true, "DETECTED");

    private boolean isError = false;
    private String label;

    PredictionType(boolean isError, String label) {
        this.isError = isError;
        this.label = label;
    }

    public boolean isError() {
        return isError;
    }

    public String getLabel() {
        return label;
    }
}
