package org.jobrunr.examples;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jobrunr.dashboard.JobRunrDashboardWebServer;
import org.jobrunr.server.BackgroundJobServer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class JobRunrExampleApplicationLifecycle {

    @Inject
    BackgroundJobServer backgroundJobServer;
    @Inject
    JobRunrDashboardWebServer dashboard;


    void onStart(@Observes StartupEvent ev) {
        System.out.println("JobRunr - Starting background job server");
        backgroundJobServer.start();
        System.out.println("JobRunr - Started background job server");
        System.out.println("JobRunr - Starting dashboard");
        dashboard.start();
        System.out.println("JobRunr - Started dashboard");
    }

    void onStop(@Observes ShutdownEvent ev) {
        System.out.println("JobRunr - Stopping background job server");
        backgroundJobServer.stop();
        System.out.println("JobRunr - Stopped background job server");
        System.out.println("JobRunr - Stopping dashboard");
        dashboard.stop();
        System.out.println("JobRunr - Stopped dashboard");
    }

}
