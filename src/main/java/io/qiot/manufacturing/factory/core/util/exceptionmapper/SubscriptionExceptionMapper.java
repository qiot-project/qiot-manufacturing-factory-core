package io.qiot.manufacturing.factory.core.util.exceptionmapper;

import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import io.qiot.manufacturing.all.commons.exception.SubscriptionException;

// TODO: Auto-generated Javadoc
/**
 * The Class SubscriptionExceptionMapper.
 * 
 * @author andreabattaglia
 */
public class SubscriptionExceptionMapper
        implements ResponseExceptionMapper<SubscriptionException> {
    
    /**
     * To throwable.
     *
     * @param r the r
     * @return the subscription exception
     */
    @Override
    public SubscriptionException toThrowable(Response r) {
        return new SubscriptionException(
                r.getStatus() + " - " + r.readEntity(String.class));
    }
}