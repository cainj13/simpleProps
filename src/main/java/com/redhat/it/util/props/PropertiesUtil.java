package com.redhat.it.util.props;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class PropertiesUtil {

	public static Stream<Map.Entry> toStream(final Properties properties) {
		return properties.stringPropertyNames().stream()
				.map(stringPropertyName -> new AbstractMap.SimpleImmutableEntry<>(stringPropertyName, properties.getProperty(stringPropertyName)));
	}
}
