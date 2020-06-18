package org.jobrunr.examples.webapp.api;

import org.jobrunr.dashboard.JobRunrDashboardWebServer;
import org.jobrunr.server.BackgroundJobServer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("backgroundjob")
@ApplicationScoped
public class BackgroundJobServerResource {

    @Inject
    BackgroundJobServer backgroundJobServer;
    @Inject
    JobRunrDashboardWebServer dashboard;

    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse start() {
        backgroundJobServer.start();
        return new SimpleResponse("JobServer started");
    }

    @GET
    @Path("/stop")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse stop() {
        backgroundJobServer.stop();

        return new SimpleResponse("JobServer stopped");
    }

}
