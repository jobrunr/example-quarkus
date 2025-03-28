package org.jobrunr.examples.jobhandlers;

import jakarta.enterprise.context.ApplicationScoped;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

@ApplicationScoped
public class MyJobRequestHandler implements JobRequestHandler<MyJobRequest> {

    @Override
    public void run(MyJobRequest jobRequest) {
        System.out.println("Doing a test with " + jobRequest.input());
    }
}
