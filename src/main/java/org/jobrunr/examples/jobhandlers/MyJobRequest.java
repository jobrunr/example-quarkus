package org.jobrunr.examples.jobhandlers;

import jakarta.json.bind.annotation.JsonbCreator;
import org.jobrunr.jobs.lambdas.JobRequest;

public class MyJobRequest implements JobRequest {

    private final String input;

    @JsonbCreator
    public MyJobRequest(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    @Override
    public Class<MyJobRequestHandler> getJobRequestHandler() {
        return MyJobRequestHandler.class;
    }
}
