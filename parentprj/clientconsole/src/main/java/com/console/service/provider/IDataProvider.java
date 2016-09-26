package com.console.service.provider;

import com.console.domain.ICallback;

/**
 *
 * @author exfaff
 */
public interface IDataProvider {

    public void getSystemState(final ICallback callback);
}
