package org.jobrunr.examples.webapp.api;

import org.jobrunr.examples.services.MyService;
import org.jobrunr.examples.services.MyServiceInterface;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.details.JobDetailsGeneratorUtils;
import org.jobrunr.scheduling.JobScheduler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

@Path("jobs")
@ApplicationScoped
public class JobResource {

    @Inject
    MyServiceInterface myService;
    @Inject
    JobScheduler jobScheduler;

    @GET
    @Path("/simple-job")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse simpleJob(@DefaultValue("World") @QueryParam("name") String name) throws IOException {
        String location = "/" + JobDetailsGeneratorUtils.toFQResource(this.getClass().getName()) + ".class";
        final InputStream resourceAsStream = getClass().getResourceAsStream(location);
        System.out.println("Data available: " + (resourceAsStream != null));

        ClassReader parser = new ClassReader(resourceAsStream);
        parser.accept(new ClassVisitor(Opcodes.ASM7) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("Visited method: " + name);
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, ClassReader.SKIP_FRAMES);

        jobScheduler.<MyService>enqueue(myService -> myService.doSimpleJob("Hello " + name));

        return new SimpleResponse("Job Enqueued");
    }

    @GET
    @Path("/simple-job-instance")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse simpleJobUsingInstance(@DefaultValue("World") @QueryParam("name") String name) {
        jobScheduler.enqueue(() -> myService.doSimpleJob("Hello " + name));

        return new SimpleResponse("Job Enqueued");
    }

    @GET
    @Path("/long-running-job")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse longRunningJob(@DefaultValue("World") @QueryParam("name") String name) {
        jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJob("Hello " + name));

        return new SimpleResponse("Job Enqueued");
    }

    @GET
    @Path("/long-running-job-with-job-context")
    @Produces(MediaType.APPLICATION_JSON)
    public SimpleResponse longRunningJobWithJobContext(@DefaultValue("World") @QueryParam("name") String name) {
        jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJobWithJobContext("Hello " + name, JobContext.Null));

        return new SimpleResponse("Job Enqueued");
    }

}
