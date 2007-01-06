/*
 * Created on 2004-11-24
 *
 */
package org.commonfarm.util;

/**
 * This exception is used to mark (fatal) failures in infrastructure and system code.
 *
 * @author Junhao Yang
 */
public class InfrastructureException
	extends NestedRuntimeException {

	public InfrastructureException() {
	}

	public InfrastructureException(String message) {
		super(message);
	}

	public InfrastructureException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfrastructureException(Throwable cause) {
		super(cause);
	}
}
