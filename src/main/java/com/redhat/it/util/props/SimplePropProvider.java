package com.redhat.it.util.props;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Optional;

@ApplicationScoped
public class SimplePropProvider {

	@Inject
	SimplePropSource propSource;

	public SimplePropProvider() {
	}

	public SimplePropProvider(final SimplePropSource propSource) {
		this.propSource = propSource;
	}

	@Produces
	@SimpleProp
	public String getSimpleProp(final InjectionPoint injectionPoint) {
		final Optional<Annotation> simplePropertyAnnotation = injectionPoint.getQualifiers().stream()
				.filter(annotation -> annotation.annotationType().isAssignableFrom(SimpleProp.class))
				.findFirst();

		if (!simplePropertyAnnotation.isPresent()) {
			throw new SimplePropertyException("Attempted to inject simple property on entity not annotated with @SimpleProp");
		}

		final Optional<String> key = Optional.ofNullable(((SimpleProp) simplePropertyAnnotation.get()).key());
		if (!key.isPresent()) {
			throw new SimplePropertyException("@SimpleProp defined without a key.  Please make sure all annotated fields and parameters have a key=\"keyname\" definition.");
		}

		final Optional<String> property = Optional.ofNullable(propSource.getProperties().getProperty(key.get()));
		return property.orElseThrow(() -> new SimplePropertyException(String.format("Could not find required property %s.", key.get())));
	}
}
