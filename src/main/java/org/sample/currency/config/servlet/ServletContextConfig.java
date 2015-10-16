package org.sample.currency.config.servlet;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
