package org.jobrunr.examples.webapp.api;

import org.jobrunr.examples.services.MyService;
import org.jobrunr.examples.services.MyServiceInterface;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.scheduling.JobScheduler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.time.Duration;

import static java.time.Instant.now;

@Path("jobs")
@ApplicationScoped
public class JobResource {

    @Inject
    MyServiceInterface myService;
    @Inject
    JobScheduler jobScheduler;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "Hello World from JobResource!<br />" +
                "You can:<br />" +
                "- <a href=\"/jobs/simple-job\">Enqueue a simple job</a><br />" +
                "- <a href=\"/jobs/simple-job-instance\">Enqueue a simple job using a service instance</a><br />" +
                "- <a href=\"/jobs/schedule-simple-job\">Schedule a simple job 3 hours from now using a service instance</a><br />" +
                "- <a href=\"/jobs/long-running-job\">Enqueue a long-running job</a><br />" +
                "- <a href=\"/jobs/long-running-job-with-job-context\">Enqueue a long-running job using a JobContext to log progress</a><br />" +
                "- Learn more on <a href=\"https://www.jobrunr.io/\">www.jobrunr.io</a><br />"
                ;
    }

    @GET
    @Path("/simple-job")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleJob(@DefaultValue("Hello world") @QueryParam("value") String value) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doSimpleJob(value));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @GET
    @Path("/simple-job-instance")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleJobUsingInstance(@DefaultValue("Hello world") @QueryParam("value") String value) {
        final JobId enqueuedJobId = jobScheduler.enqueue(() -> myService.doSimpleJob(value));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @GET
    @Path("/schedule-simple-job")
    @Produces(MediaType.TEXT_PLAIN)
    public String scheduleSimpleJob(
            @DefaultValue("Hello world") @QueryParam("value") String value,
            @DefaultValue("PT3H") @QueryParam("when") String when) {
        final JobId scheduledJobId = jobScheduler.schedule(now().plus(Duration.parse(when)), () -> myService.doSimpleJob(value));
        return "Job Scheduled: " + scheduledJobId;
    }

    @GET
    @Path("/long-running-job")
    @Produces(MediaType.TEXT_PLAIN)
    public String longRunningJob(@DefaultValue("Hello world") @QueryParam("value") String value) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJob(value));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @GET
    @Path("/long-running-job-with-job-context")
    @Produces(MediaType.TEXT_PLAIN)
    public String longRunningJobWithJobContext(@DefaultValue("Hello world") @QueryParam("value") String value) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJobWithJobContext(value, JobContext.Null));
        return "Job Enqueued: " + enqueuedJobId;
    }

}
