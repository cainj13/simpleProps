package com.redhat.it.util.props.provider;

import com.redhat.it.util.props.PropertiesUtil;
import com.redhat.it.util.props.SimplePropSource;

import java.util.Arrays;
import java.util.Properties;

public class CombinedSimplePropertySource implements SimplePropSource {

	final Properties properties = new Properties();

	public CombinedSimplePropertySource(SimplePropSource... propSources) {
		Arrays.stream(propSources)
				.flatMap(simplePropSource -> PropertiesUtil.toStream(simplePropSource.getProperties()))
				.forEach(propertyEntry -> properties.putIfAbsent(propertyEntry.getKey(), propertyEntry.getValue()));
	}

	@Override
	public Properties getProperties() {
		return new Properties(properties);
	}
}
