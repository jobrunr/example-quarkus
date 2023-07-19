package org.jobrunr.examples.services;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.quarkus.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * This is a simple service
 */
@ApplicationScoped
@RegisterForReflection
public class MyService implements MyServiceInterface {

    private static final Logger LOGGER = new JobRunrDashboardLogger(LoggerFactory.getLogger(MyService.class));

    @Recurring(id = "my-recurring-job", cron = "*/15 * * * *")
    @Job(name = "Doing some work")
    public void aRecurringJob() {
        System.out.println("Doing some work every 15 minutes.");
    }

    public void doSimpleJob(String anArgument) {
        System.out.println("Doing some work: " + anArgument);
    }

    public void doLongRunningJob(String anArgument) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Doing work item %d: %s", i, anArgument));
                Thread.sleep(20000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void doLongRunningJobWithJobContext(String anArgument, JobContext jobContext) {
        try {
            LOGGER.warn("Starting long running job...");
            final JobDashboardProgressBar progressBar = jobContext.progressBar(10);
            for (int i = 0; i < 10; i++) {
                LOGGER.info(String.format("Processing item %d: %s", i, anArgument));
                System.out.println(String.format("Doing work item %d: %s", i, anArgument));
                Thread.sleep(15000);
                progressBar.increaseByOne();
            }
            LOGGER.warn("Finished long running job...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

