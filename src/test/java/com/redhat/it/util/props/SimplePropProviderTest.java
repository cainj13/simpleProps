package com.redhat.it.util.props;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(CdiRunner.class)
@AdditionalClasses({SimplePropProvider.class, TestPropertySource.class})
public class SimplePropProviderTest {

	@Inject
	@SimpleProp(key = "os.name")
	String operatingSystemName;

	@Test
	public void shouldProvideSimpleProp() {
		assertThat(operatingSystemName, equalTo(System.getProperty("os.name")));
	}
}
