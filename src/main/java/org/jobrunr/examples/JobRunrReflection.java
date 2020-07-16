package org.jobrunr.examples;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jobrunr.jobs.lambdas.JobRunrJob;

@RegisterForReflection(targets = JobRunrJob.class)
public class JobRunrReflection {
}
