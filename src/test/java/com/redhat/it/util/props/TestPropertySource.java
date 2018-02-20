package com.redhat.it.util.props;

import com.redhat.it.util.props.provider.SystemPropertySimplePropSource;

import javax.enterprise.inject.Produces;

public class TestPropertySource {

	@Produces
	public SimplePropSource getTestPropSource() {
		return new SystemPropertySimplePropSource();
	}
}
