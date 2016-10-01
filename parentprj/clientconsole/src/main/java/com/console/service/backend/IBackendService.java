package com.console.service.backend;

import com.console.service.IService;

/**
 *
 * @author Fabrizio Faustinoni
 */
interface IBackendService extends IService {

    void start() throws BackEndServiceException;
}
