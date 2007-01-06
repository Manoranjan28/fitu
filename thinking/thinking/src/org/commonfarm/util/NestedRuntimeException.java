/*
 * Created on 2005-2-21
 */
package org.commonfarm.util;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author Junhao Yang
 *
 */
public abstract class NestedRuntimeException extends RuntimeException {

	/** Root cause of this nested exception */
	private Throwable cause;

	/**
	 * Construct a <code>NestedRuntimeException</code> with the specified detail message.
	 * @param msg the detail message
	 */
	public NestedRuntimeException(String msg) {
		super(msg);
	}

	/**
	 * Construct a <code>NestedRuntimeException</code> with the specified detail message
	 * and nested exception.
	 * @param msg the detail message
	 * @param ex the nested exception
	 */
	public NestedRuntimeException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	public NestedRuntimeException(Throwable ex) {
		super();
		this.cause = ex;
	}
	
	public NestedRuntimeException() {
		super();
	}
	
	/**
	 * Return the nested cause, or null if none.
	 */
	public Throwable getCause() {
		return (this.cause == this ? null : this.cause);
	}

	/**
	 * Return the detail message, including the message from the nested exception
	 * if there is one.
	 */
	public String getMessage() {
		// Even if you cannot set the cause of this exception other than through
		// the constructor, we check for the cause being "this" here, as the cause
		// could still be set to "this" via reflection: for example, by a remoting
		// deserializer like Hessian's.
		if (this.cause == null || this.cause == this) {
			return super.getMessage();
		}
		else {
			return super.getMessage() + "; nested exception is " + this.cause.getClass().getName() +
					": " + this.cause.getMessage();
		}
	}

	/**
	 * Print the composite message and the embedded stack trace to the specified stream.
	 * @param ps the print stream
	 */
	public void printStackTrace(PrintStream ps) {
		if (this.cause == null || this.cause == this) {
			super.printStackTrace(ps);
		}
		else {
			ps.println(this);
			this.cause.printStackTrace(ps);
		}
	}

	/**
	 * Print the composite message and the embedded stack trace to the specified writer.
	 * @param pw the print writer
	 */
	public void printStackTrace(PrintWriter pw) {
		if (this.cause == null || this.cause == this) {
			super.printStackTrace(pw);
		}
		else {
			pw.println(this);
			this.cause.printStackTrace(pw);
		}
	}

}

