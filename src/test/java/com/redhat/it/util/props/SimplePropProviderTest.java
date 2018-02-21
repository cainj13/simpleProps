package com.redhat.it.util.props;

import com.redhat.it.util.props.provider.SystemPropertySimplePropSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SimplePropProviderTest {

	SimplePropProvider simplePropProvider;
	@Mock
	InjectionPoint injectionPoint;
	@Mock
	SimpleProp simpleProp;

	@SimpleProp(key = "os.name")
	String mySimpleProp;
	@SimpleProp(key = "does-not-exist")
	String propWhoseKeyIsAbsent;
	@SimpleProp
	String propWithNoKey;

	@Before
	public void setUp() throws Exception {
		simplePropProvider = new SimplePropProvider(new SystemPropertySimplePropSource());
		initMocks(this);
	}

	@Test
	public void shouldProvideSimplePropertyWhenValid() throws Exception {
		final SimpleProp simpleProp = getClass().getDeclaredField("mySimpleProp").getAnnotation(SimpleProp.class);
		when(injectionPoint.getQualifiers()).thenReturn(Collections.singleton(simpleProp));

		assertThat(simplePropProvider.getSimpleProp(injectionPoint), equalTo(System.getProperty("os.name")));
	}

	@Test(expected = SimplePropertyException.class)
	public void shouldExceptionWhenSimplePropAnnotationIsAbsent() throws Exception {
		when(injectionPoint.getQualifiers()).thenReturn(Collections.emptySet());

		simplePropProvider.getSimpleProp(injectionPoint);
		fail("Injection point with no qualifiers did not result in exception");
	}

	@Test(expected = SimplePropertyException.class)
	public void shouldExceptionWhenSimplePropFieldDefinedWithoutKey() throws Exception {
		final SimpleProp simpleProp = getClass().getDeclaredField("propWithNoKey").getAnnotation(SimpleProp.class);
		when(injectionPoint.getQualifiers()).thenReturn(Collections.singleton(simpleProp));

		simplePropProvider.getSimpleProp(injectionPoint);
		fail("Injection point with no key did not result in exception");
	}

	@Test(expected = SimplePropertyException.class)
	public void shouldExceptionWhenRequredPropertyNotFound() throws Exception {
		final SimpleProp simpleProp = getClass().getDeclaredField("propWhoseKeyIsAbsent").getAnnotation(SimpleProp.class);
		when(injectionPoint.getQualifiers()).thenReturn(Collections.singleton(simpleProp));

		simplePropProvider.getSimpleProp(injectionPoint);
		fail("Injection point with no property value did not result in exception");
	}
}
