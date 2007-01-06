package org.commonfarm.security.interceptor;

import org.commonfarm.security.Initable;

/**
 * The base interface for Seraph interceptors. Interceptors allow you to run code before and after security events.
 */
public interface Interceptor extends Initable
{
    public void destroy();
}
