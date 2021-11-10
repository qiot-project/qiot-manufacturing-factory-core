package io.qiot.manufacturing.factory.core.service.registration;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.qiot.manufacturing.all.commons.domain.landscape.SubscriptionResponse;
import io.qiot.manufacturing.all.commons.exception.SubscriptionException;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.FactorySubscriptionRequest;
import io.qiot.manufacturing.datacenter.commons.domain.subscription.MachinerySubscriptionRequest;

/**
 * @author andreabattaglia
 *
 */
@Path("/v1")
@RegisterRestClient(configKey = "plant-manager-api")
@RegisterProvider(SubscriptionException.class)
public interface PlantManagerClient {

    @PUT
    @Path("/factory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SubscriptionResponse subscribeFactory(
            FactorySubscriptionRequest request);

    @PUT
    @Path("/machinery")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SubscriptionResponse subscribeMachinery(
            MachinerySubscriptionRequest request);

}