package org.gaussian.supersonic.session.configuration;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@Slf4j
@ApplicationScoped
public class AppLifecycleBean {

    void onStart(@Observes StartupEvent ev) {
        log.info("The application SUSE is starting with profile `%s`", ProfileManager.getActiveProfile());
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("The application is stopping...");
    }

}