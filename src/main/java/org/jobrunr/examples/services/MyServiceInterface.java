package org.jobrunr.examples.services;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public interface MyServiceInterface {

    void doSimpleJob(String anArgument);

}
