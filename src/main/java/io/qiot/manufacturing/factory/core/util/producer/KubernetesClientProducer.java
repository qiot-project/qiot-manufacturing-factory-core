package io.qiot.manufacturing.factory.core.util.producer;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;

@Singleton
public class KubernetesClientProducer {

    @Produces
    public DefaultKubernetesClient kubernetesClient() {
        // here you would create a custom client
        return new DefaultKubernetesClient();
    }
}