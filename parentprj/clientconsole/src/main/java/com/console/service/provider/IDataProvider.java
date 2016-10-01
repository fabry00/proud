package com.console.service.provider;

import com.console.domain.ICallback;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IDataProvider {

    void getSystemState(final ICallback callback);
}
