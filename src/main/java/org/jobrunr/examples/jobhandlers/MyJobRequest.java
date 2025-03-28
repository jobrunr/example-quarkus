package org.jobrunr.examples.jobhandlers;

import jakarta.json.bind.annotation.JsonbCreator;
import org.jobrunr.jobs.lambdas.JobRequest;

public record MyJobRequest(String input) implements JobRequest {

    @JsonbCreator
    public MyJobRequest {
    }

    @Override
    public Class<MyJobRequestHandler> getJobRequestHandler() {
        return MyJobRequestHandler.class;
    }
}
