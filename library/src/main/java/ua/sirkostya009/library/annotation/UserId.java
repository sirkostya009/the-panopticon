package ua.sirkostya009.library.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for extracting "uid" claim from Bearer token and injecting it into a String parameter.
 * @see ua.sirkostya009.library.resolver.UserIdResolver UserIdResolver
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
}
