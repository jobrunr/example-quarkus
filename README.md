# JobRunr Quarkus example

This repository shows an example how you can integrate JobRunr and [Quarkus](https://quarkus.io/).

## About this project
This project has the following packages:
- **org.jobrunr.examples**: this package contains 3 classes:
  - `JobRunrExampleApplication`: the EE application that defines the context path
  - `JobRunrExampleApplicationLifecycle`: this class makes sure the background job server and dashboard are started correctly
  - `JobRunrProvider`: a class that produces various beans that can be consumed in the http resources. 
- **org.jobrunr.examples.services**: this package contains [MyService](src/main/java/org/jobrunr/examples/services/MyService.java), a simple ApplicationScoped service with two example methods which you can run in the background.  
- **org.jobrunr.examples.webap.api**: this package contains the following http resource:
  - `JobResource`: this resource contains two REST api's which allows you to enqueue new Background Jobs

## How to run this project:
- clone the project and open it in your favorite IDE that supports Maven
- Run the Maven plugin `mvn quarkus:dev` and wait for Quarkus to be up & running
- Open your favorite browser:
  - Navigate to the JobRunr dashboard located at http://localhost:8000/dashboard.
  - To enqueue a simple job, open a new tab and go to http://localhost:8080/jobrunr/jobs/simple-job?name=your-name
  - To enqueue a long running job, open a new tab and go to http://localhost:8080/jobrunr/jobs/long-running-job?name=your-name
  - Visit the dashboard again and see the jobs being processed!

> Note: Running quarkus apps with JobRunr natively is not yet supported. I'm currently looking into writing an extension to make this possible. See https://mail.google.com/mail/u/0/#inbox/FMfcgxwHNqMgLkzQfbqjwcFdpZbRdvrQ for more info