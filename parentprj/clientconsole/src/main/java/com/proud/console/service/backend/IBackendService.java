package com.proud.console.service.backend;

import com.proud.console.service.IService;

/**
 *
 * @author Fabrizio Faustinoni
 */
interface IBackendService extends IService {

    void start() throws BackEndServiceException;
}
