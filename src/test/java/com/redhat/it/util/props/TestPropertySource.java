package com.redhat.it.util.props;

import com.redhat.it.util.props.provider.ClasspathPropertyFileSimplePropSource;
import com.redhat.it.util.props.provider.CombinedSimplePropertySource;
import com.redhat.it.util.props.provider.FileSimplePropertySource;
import com.redhat.it.util.props.provider.SystemPropertySimplePropSource;

import javax.enterprise.inject.Produces;
import java.io.File;

public class TestPropertySource {

	@Produces
	public SimplePropSource getTestPropSource() {
		return new CombinedSimplePropertySource(new SystemPropertySimplePropSource(),
				new ClasspathPropertyFileSimplePropSource("classpath.properties"),
				new FileSimplePropertySource(new File("src/test/resources/file.properties")));
	}
}
