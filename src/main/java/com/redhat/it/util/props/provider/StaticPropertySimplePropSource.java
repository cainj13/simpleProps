package com.redhat.it.util.props.provider;

import com.redhat.it.util.props.SimplePropSource;

import java.util.Properties;

public class StaticPropertySimplePropSource implements SimplePropSource {

	private final Properties properties;

	public StaticPropertySimplePropSource(final Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getProperties() {
		return new Properties(properties);
	}
}
