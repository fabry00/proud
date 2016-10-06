package com.proud.server.resource;

import com.proud.domain.PredictionType;

/**
 * System Status
 * Created by Fabrizio Faustinoni on 03/10/2016.
 */
public class SystemStatus {

    private PredictionType failurePrediction;
    private PredictionType systemFailure;

    public SystemStatus(PredictionType failurePrediction, PredictionType systemFailure){
        this.failurePrediction = failurePrediction;
        this.systemFailure = systemFailure;
    }

    public PredictionType getFailurePrediction() {
        return failurePrediction;
    }

    public PredictionType getSystemFailure() {
        return systemFailure;
    }
}
