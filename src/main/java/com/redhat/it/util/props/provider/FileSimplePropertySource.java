package com.redhat.it.util.props.provider;

import com.redhat.it.util.props.SimplePropSource;
import com.redhat.it.util.props.SimplePropertyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileSimplePropertySource implements SimplePropSource {

	private final Properties properties;

	public FileSimplePropertySource(final File propertyFile) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertyFile));
		} catch (IOException | RuntimeException e) {
			throw new SimplePropertyException("Unable to load properties from source file: " + propertyFile, e);
		}
	}

	@Override
	public Properties getProperties() {
		return new Properties(properties);
	}
}
