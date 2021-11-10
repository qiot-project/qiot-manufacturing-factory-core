package io.qiot.manufacturing.factory.core.service.registration;

import java.util.UUID;

import io.qiot.manufacturing.all.commons.exception.SubscriptionException;

/**
 * @author andreabattaglia
 *
 */
public interface RegistrationService {

    UUID register(String serial, String name, String ksPassword)
            throws Exception;
}