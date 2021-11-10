package io.qiot.manufacturing.factory.core.service.registration;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.all.commons.exception.SubscriptionException;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.FactorySubscriptionRequest;
import io.qiot.ubi.all.registration.client.RegistrationServiceClient;
import io.qiot.ubi.all.registration.domain.CAIssuerRequest;

/**
 * @author andreabattaglia
 *
 */
@ApplicationScoped
public class RegistrationServiceImpl implements RegistrationService {
    @Inject
    Logger LOGGER;

    @Inject
    @RestClient
    PlantManagerClient plantManagerClient;

    @Inject
    @RestClient
    RegistrationServiceClient registrationServiceClient;

    @Override
    public UUID register(String serial, String name, String ksPassword)
            throws SubscriptionException {
        SubscriptionResponse subscriptionResponse = null;
        FactorySubscriptionRequest registerRequest = new FactorySubscriptionRequest();

        registerRequest.serial = serial;
        registerRequest.name = name;
        registerRequest.keyStorePassword = ksPassword;

        LOGGER.debug(
                "Attempting subscription process with the following data: {}",
                registerRequest);

//        while (subscriptionResponse == null) {
            // TODO: put sleep time in application.properties
            long sleepTime = 2000;
            try {

                subscriptionResponse = plantManagerClient
                        .subscribeFactory(registerRequest);
            } catch (Exception e) {
                LOGGER.info(
                        "An error occurred registering the factory. "
                                + "Retrying in {} millis.\n Error message: {}",
                        sleepTime, e.getMessage());
//                try {
//                    Thread.sleep(sleepTime);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                }
//            }
        }

        // create factory issuer
        CAIssuerRequest issuerRequest = new CAIssuerRequest();
        issuerRequest.tlsCert = subscriptionResponse.tlsCert;
        issuerRequest.tlsKey = subscriptionResponse.tlsKey;
        registrationServiceClient.provisionIssuer(issuerRequest);

        LOGGER.debug("Registratior process results: {}", subscriptionResponse);

        return subscriptionResponse.id;
    }
}
