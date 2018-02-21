# simpleProps
Dead simple J2EE properties injection support.

Ever wish there was a really simple way to inject properties into beans from the classpath?  A local file?  Us too.  simpleProps is a quick-and-dirty implementation aimed at doing the minimum necessary to import some properties into your J2EE application, so you can get on with your life and quit worrying about configuring properties.

## Quickstart
Enough talk already, show me how to use it!

#### 1) Import simpleProps dependency
// TODO pulbish on Maven Central, include artifact import instructions here.  Until then, you're stuck compiling from source :(

#### 2) Define your property sources
Begin by creating a factory bean on the CDI classpath that `@Produces` a `SimplePropSource`.  It might look something like this:

```
import com.redhat.it.util.props.provider.ClasspathPropertyFileSimplePropSource;
import com.redhat.it.util.props.provider.CombinedSimplePropertySource;
import com.redhat.it.util.props.provider.FileSimplePropertySource;
import com.redhat.it.util.props.provider.SystemPropertySimplePropSource;

import javax.enterprise.inject.Produces;
import java.io.File;

public class MyPropertySource {

	@Produces
	public SimplePropSource getMyPropSource() {
		return new CombinedSimplePropertySource(new SystemPropertySimplePropSource(),
				new ClasspathPropertyFileSimplePropSource("classpath.properties"),
				new FileSimplePropertySource(new File("/opt/resources/file.properties")));
	}
}
```

** Note - make sure CDI is properly enabled for your J2EE deployment.  If this is not supported by the container, or is not enabled by your deployment (looking at you, beans.xml!), this property source will never get picked up.

#### 3) Define your properties
Once your property sources have been defined, all you have to do is mark the property values you want injected with their respective keys:

```
import com.redhat.it.util.props.SimpleProp;
import javax.inject.Inject;

public class MyResource {

    @Inject
    @SimpleProp(key = "os.name")
    String operatingSystemName;

    ...
    // do cool stuff with your properties
    ...

}
```

## Customizing Behavior
The `@SimpleProp` Annotation has a few flags that let you customize how the injection mechanism behaves under various circumstances:

#### Optional Properties
By default, all properties are assumed to be required, and simpleProps will throw an exception if they're not found.  This can be circumvented by flipping the `required` flag to `false`.

```
    @Inject
    @SimpleProp(key = "optional.property", required = false)
    String optionalProperty;
```

#### Default Values
Similarly, default values can be provided for use when no matching key is found by the `SimplePropSource`.
```
    @Inject
    @SimpleProp(key = "use-defualt", defaultValue = "default")
    String useDefaultValue;
```
** If both `required = false` and a `defaultValue` are given, the `defaultValue` will be used.