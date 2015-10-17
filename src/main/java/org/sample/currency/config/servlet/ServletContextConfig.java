package org.sample.currency.config.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Spring MVC config for the servlet context in the application.
 * <p/>
 * The beans of this context are only visible inside the servlet context.
 *
 * Created by Mohamed Mekkawy.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.sample.currency.app.controller")
public class ServletContextConfig extends WebMvcConfigurerAdapter {

    @Value("#{'${cache.urls}'.split(',')}")
    private List<String> configuredCaches;

    private Map<String, String> configuredCachesMap;

    @Value("${cache.static}")
    private Integer staticContentCache;

    private final Logger logger = LoggerFactory.getLogger(ServletContextConfig.class);

    @PostConstruct
    public void init() {
        configuredCachesMap = new HashMap();
        if (configuredCaches != null && configuredCaches.size() != 0) {
            for (String field : configuredCaches) {
                String[] splittedField = field.split("=");
                configuredCachesMap.put(splittedField[0], splittedField[1]);
            }
        }
    }



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addRedirectViewController("/", "/resources/public/login.html");
    }

    /**
     * Configuring static content.
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Static content configured to be cached for {} seconds", staticContentCache);
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(staticContentCache);
    }

    /**
     * Setting interceptor with configured dynamic content caches as specified in the properties.
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("Configuring URL Caching as follow {}", configuredCachesMap);
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        Properties cacheMappings = new Properties();
        cacheMappings.putAll(configuredCachesMap);
        webContentInterceptor.setCacheMappings(cacheMappings);
        registry.addInterceptor(webContentInterceptor);
    }

}
