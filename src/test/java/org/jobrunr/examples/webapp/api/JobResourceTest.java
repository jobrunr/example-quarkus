package org.jobrunr.examples.webapp.api;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.jobrunr.jobs.states.StateName.SCHEDULED;
import static org.jobrunr.jobs.states.StateName.SUCCEEDED;
import static org.jobrunr.utils.StringUtils.substringAfter;

@QuarkusTest
class JobResourceTest {

    static {
        RestAssured.baseURI = "http://localhost:8081/";
    }

    @Inject
    StorageProvider storageProvider;

    @Test
    public void testEnqueueSimpleJob() {
        RequestSpecification httpRequest = RestAssured.given();
        String responseBody = httpRequest.get("/jobs/simple-job").getBody().asString();
        assertThat(responseBody).startsWith("Job Enqueued: ");

        final UUID enqueuedJobId = UUID.fromString(substringAfter(responseBody, ": "));

        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getJobById(enqueuedJobId).hasState(SUCCEEDED));
    }

    @Test
    public void testScheduleSimpleJob() {
        RequestSpecification httpRequest = RestAssured.given();
        String responseBody = httpRequest.get("/jobs/schedule-simple-job?when=" + Duration.of(3, ChronoUnit.HOURS)).getBody().asString();
        assertThat(responseBody).startsWith("Job Scheduled: ");

        final UUID scheduledJobId = UUID.fromString(substringAfter(responseBody, ": "));
        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getJobById(scheduledJobId).hasState(SCHEDULED));
    }

}