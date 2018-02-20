package com.redhat.it.util.props;

public class SimplePropertyException extends RuntimeException {

	public SimplePropertyException(final String message) {
		super(message);
	}

	public SimplePropertyException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SimplePropertyException(final Throwable cause) {
		super(cause);
	}

	protected SimplePropertyException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
