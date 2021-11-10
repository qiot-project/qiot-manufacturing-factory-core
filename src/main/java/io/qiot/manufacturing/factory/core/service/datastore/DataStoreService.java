/**
 * 
 */
package io.qiot.manufacturing.factory.core.service.datastore;

import io.qiot.manufacturing.all.commons.exception.DataValidationException;
import io.qiot.manufacturing.factory.core.domain.dto.FactoryDataDTO;

/**
 * @author andreabattaglia
 *
 */
public interface DataStoreService {
    void saveFactortData(FactoryDataDTO data) throws DataValidationException;
    
    FactoryDataDTO loadFactoryData() throws DataValidationException;
}
