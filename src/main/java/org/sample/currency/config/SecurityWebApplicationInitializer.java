package org.sample.currency.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Sets up the Spring Security filter chain
 *
 * Created by Mohamed Mekkawy.
 */
@Order(2)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}

