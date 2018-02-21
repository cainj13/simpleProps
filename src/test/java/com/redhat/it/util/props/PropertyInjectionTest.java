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
public class PropertyInjectionTest {

	@Inject
	@SimpleProp(key = "os.name")
	String operatingSystemName;

	@Inject
	@SimpleProp(key = "troll.question.name")
	String trollNameAnswer;

	@Inject
	@SimpleProp(key = "average.airspeed.velocity.swallow.european")
	String averageAirspeedVelocityEuropean;

	@Test
	public void shouldProvideSimpleProp() {
		assertThat(operatingSystemName, equalTo(System.getProperty("os.name")));
	}

	@Test
	public void shouldProvideClasspathProp() {
		assertThat(trollNameAnswer, equalTo("Sir Lancelot of Camelot"));
	}

	@Test
	public void shouldProvideFileProp() {
		assertThat(averageAirspeedVelocityEuropean, equalTo("50"));
	}
}