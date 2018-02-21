package com.redhat.it.util.props;

import java.util.Properties;

public interface SimplePropSource {

	/**
	 * @return Copy of properties represented by the given property source
	 */
	Properties getProperties();

}
