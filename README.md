# JobRunr Quarkus example

This repository shows an example how you can integrate JobRunr and [Quarkus](https://quarkus.io/).

## About this project

This project has the following packages:

- **org.jobrunr.examples**: this package contains 3 classes:
  - `JobRunrExampleApplication`: the EE application that defines the context path 
- **org.jobrunr.examples.services**: this package contains [MyService](src/main/java/org/jobrunr/examples/services/MyService.java), a simple ApplicationScoped service with two example methods which you can run in the background.  
- **org.jobrunr.examples.webap.api**: this package contains the following http resource:
  - `JobResource`: this resource contains two REST api's which allows you to enqueue new Background Jobs

## How to run this project

- clone the project and open it in your favorite IDE that supports Maven
- Run the Maven plugin `mvn quarkus:dev` and wait for Quarkus to be up & running
- Open your favorite browser:
  - Navigate to the JobRunr dashboard located at http://localhost:8000/dashboard.
  - To enqueue a simple job, open a new tab and go to http://localhost:8080/jobs/ and take it for there.
  - Visit the dashboard again and see the jobs being processed!

### Compiling it natively

Quarkus is optimized for GraalVM and JobRunr supports this, except for enqueueing lambdas using the jobscheduler because of ASM analysis trickery. To see how this works, you can compile this example project natively (see the [Quarkus documentation](https://quarkus.io/guides/building-native-image) for more details):

1. Install a GraalVM VM and have `GRAALVM_HOME` set up
2. Build the project using `quarkus build --native` or `./mvnw install -Dnative`. The native profile in the `pom.xml` has native specific properties enabled. 
3. in `target`, you should find an executable called `example-quarkus-1.0.0-SNAPSHOT-runner`. 
4. Try to schedule jobs using different ways by clicking on the links in http://localhost:8080/jobs/.

If you run into this exception:

```
Caused by: java.lang.NoSuchMethodException: org.jobrunr.examples.webapp.api.JobResource$$Lambda/0x599e42de00e955732588625a22221761.writeReplace()
  at java.base@23.0.2/java.lang.Class.checkMethod(DynamicHub.java:1158)
  at java.base@23.0.2/java.lang.Class.getDeclaredMethod(DynamicHub.java:1284)
  at org.jobrunr.jobs.details.SerializedLambdaConverter.toSerializedLambda(SerializedLambdaConverter.java:28)
```

You have attempted to schedule a lambda in native mode and JobRunr cannot analyse the metadata to store the job. Instead, rely on `@Job` or the job request method and avoid injecting and using the `JobScheduler` directly.
