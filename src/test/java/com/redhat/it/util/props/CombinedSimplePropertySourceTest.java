package com.redhat.it.util.props;

import com.redhat.it.util.props.provider.CombinedSimplePropertySource;
import com.redhat.it.util.props.provider.StaticPropertySimplePropSource;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CombinedSimplePropertySourceTest {

	@Test
	public void shouldCombinePropertiesFromAllSources() {
		final Properties properties1 = new Properties();
		properties1.put("A", "a");
		properties1.put("C", "c");

		final Properties properties2 = new Properties();
		properties2.put("B", "b");
		properties2.put("D", "d");

		final SimplePropSource combinedSimplePropSource = new CombinedSimplePropertySource(new StaticPropertySimplePropSource(properties1),
				new StaticPropertySimplePropSource(properties2));

		assertThat(combinedSimplePropSource.getProperties().getProperty("A"), equalTo("a"));
		assertThat(combinedSimplePropSource.getProperties().getProperty("B"), equalTo("b"));
		assertThat(combinedSimplePropSource.getProperties().getProperty("C"), equalTo("c"));
		assertThat(combinedSimplePropSource.getProperties().getProperty("D"), equalTo("d"));
	}

	@Test
	public void shouldMaintainPropertyPriorityForGivenSources() {
		final Properties properties1 = new Properties();
		properties1.put("A", "a-1");
		final Properties properties2 = new Properties();
		properties2.put("A", "a-2");

		final SimplePropSource a1FirstPropertySource = new CombinedSimplePropertySource(new StaticPropertySimplePropSource(properties1),
				new StaticPropertySimplePropSource(properties2));
		final SimplePropSource a2FirstPropertySource = new CombinedSimplePropertySource(new StaticPropertySimplePropSource(properties2),
				new StaticPropertySimplePropSource(properties1));

		assertThat(a1FirstPropertySource.getProperties().getProperty("A"), equalTo("a-1"));
		assertThat(a2FirstPropertySource.getProperties().getProperty("A"), equalTo("a-2"));
	}
}
