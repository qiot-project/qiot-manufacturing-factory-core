/**
 * 
 */
package io.qiot.manufacturing.factory.core.service.core;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import io.qiot.manufacturing.all.commons.exception.DataValidationException;
import io.qiot.manufacturing.all.commons.exception.SubscriptionException;
import io.qiot.manufacturing.factory.core.domain.dto.FactoryDataDTO;
import io.qiot.manufacturing.factory.core.service.datastore.DataStoreService;
import io.qiot.manufacturing.factory.core.service.registration.RegistrationService;
import io.quarkus.runtime.StartupEvent;

/**
 * @author andreabattaglia
 *
 */
// @Startup(1)
@ApplicationScoped
public class CoreServiceImpl implements CoreService {

    @Inject
    Logger LOGGER;

    @Inject
    RegistrationService registrationService;

    @ConfigProperty(name = "qiot.factory.serial")
    String FACILITY_SERIAL;
    @ConfigProperty(name = "qiot.factory.name")
    String FACILITY_NAME;

    @ConfigProperty(name = "qiot.certstore.password")
    String ksPassword;

    @Inject
    DataStoreService dataStoreService;

    private FactoryDataDTO factoryData;

    void onStart(@Observes StartupEvent ev) throws Exception {
        LOGGER.debug("The application is starting...{}");
        checkRegistration();
    }

    private void checkRegistration() throws Exception {
        factoryData = dataStoreService.loadFactoryData();
        if (factoryData == null) {
            LOGGER.info("Factory is not registered. "
                    + "Stepping through the registration process...");

            factoryData = new FactoryDataDTO();
            factoryData.serial = FACILITY_SERIAL;
            factoryData.name = FACILITY_NAME;
            UUID factoryId = null;
            // if (ProfileManager.getActiveProfile()
            // .equals(LaunchMode.DEVELOPMENT.getDefaultProfile())) {
            // factoryId = UUID.randomUUID();
            // }
            // else

            try {
                factoryId = registrationService.register(factoryData.serial,
                        factoryData.name, ksPassword);

                LOGGER.debug("Received factory ID: {}", factoryId);

                factoryData.id = factoryId;

                dataStoreService.saveFactortData(factoryData);

                LOGGER.debug("Data Created successfully: {}", factoryData);
            } catch (Exception e) {

                LOGGER.info("SOMETHING WENT WRONG", e);
            }
        }

    }
}
