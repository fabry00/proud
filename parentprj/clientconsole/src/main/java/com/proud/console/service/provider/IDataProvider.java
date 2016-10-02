package com.proud.console.service.provider;

import com.proud.console.domain.ICallback;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IDataProvider {

    void getSystemState(final ICallback callback);
}
