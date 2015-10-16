package org.sample.currency.config;


import org.sample.currency.config.root.AppSecurityConfig;
import org.sample.currency.config.root.DevelopmentConfiguration;
import org.sample.currency.config.root.RootContextConfig;
import org.sample.currency.config.servlet.ServletContextConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootContextConfig.class, DevelopmentConfiguration.class,
                AppSecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ServletContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //By default use the development profile, if no other profiles activated.
        servletContext.setInitParameter("spring.profiles.active", "development");
    }
}


