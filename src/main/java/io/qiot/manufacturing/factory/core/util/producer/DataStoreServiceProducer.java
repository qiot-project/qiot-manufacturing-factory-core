package io.qiot.manufacturing.factory.core.util.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.qiot.manufacturing.factory.core.service.SecretOperation;
import io.qiot.manufacturing.factory.core.service.datastore.ContainerDataStoreService;
import io.qiot.manufacturing.factory.core.service.datastore.DataStoreService;
import io.qiot.manufacturing.factory.core.service.datastore.KubernetesDataStoreService;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.configuration.ProfileManager;

/**
 * @author andreabattaglia
 */
@ApplicationScoped
public class DataStoreServiceProducer {

    @Inject
    Logger LOGGER;

    @Inject
    ObjectMapper MAPPER;

    @ConfigProperty(name = "qiot.datafile.path")
    String dataFilePathString;

    @ConfigProperty(name = "qiot.data.reset")
    boolean DO_RESET;

    @Inject
    SecretOperation secretOperation;

    @Produces
    public DataStoreService getCertificateService() {
        if (ProfileManager.getActiveProfile()
                .equals(LaunchMode.NORMAL.getDefaultProfile())) {
            LOGGER.debug(
                    "Active profile is: {}. Running on a Kubernates environment");
            return new KubernetesDataStoreService(LOGGER, MAPPER, secretOperation);
        } else {
            LOGGER.debug(
                    "Active profile is: {}. Running on a local/container-engine environment");
            return new ContainerDataStoreService(LOGGER, MAPPER,
                    dataFilePathString, DO_RESET);
        }
    }

}