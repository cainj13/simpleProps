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

		final SimpleProp simpleProp = (SimpleProp) simplePropertyAnnotation.get();
		final Optional<String> key = Optional.ofNullable(simpleProp.key());
		if (!key.isPresent()) {
			throw new SimplePropertyException("@SimpleProp defined without a key.  Please make sure all annotated fields and parameters have a key=\"keyname\" definition.");
		}

		final Optional<String> property = Optional.ofNullable(propSource.getProperties().getProperty(key.get()));

		if (property.isPresent()) {
			return property.get();
		} else { // No property found in sources, check default + required status and respond appropriately
			if (!simpleProp.defaultValue().isEmpty()) {
				return simpleProp.defaultValue();
			} else if (simpleProp.required()) {
				throw new SimplePropertyException(String.format("Could not find required property %s.  Either provide the property, or mark it as not required.", key.get()));
			} else {
				return null;
			}
		}
	}
}
