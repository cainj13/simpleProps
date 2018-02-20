package com.redhat.it.util.props.provider;

import com.redhat.it.util.props.SimplePropSource;

import java.util.Properties;

public class SystemPropertySimplePropSource implements SimplePropSource {

	@Override
	public Properties getProperties() {
		return System.getProperties();
	}
}
