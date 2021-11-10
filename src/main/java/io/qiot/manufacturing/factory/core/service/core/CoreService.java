package io.qiot.manufacturing.factory.core.service.core;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;

import io.qiot.manufacturing.factory.core.domain.dto.CheckRegistrationEventDTO;
import io.quarkus.runtime.StartupEvent;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class CoreService {

    @Inject
    Logger LOGGER;

    @Inject
    Event<CheckRegistrationEventDTO> event;

    void onStart(@Observes StartupEvent ev) throws Exception {
        LOGGER.debug("The application is starting...{}");
        event.fire(new CheckRegistrationEventDTO());
    }

}
