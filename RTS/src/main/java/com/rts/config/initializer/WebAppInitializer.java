package com.rts.config.initializer;

import com.rts.config.AppConfig;
import com.rts.web.config.MvcConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Order(2)
public class WebAppInitializer implements WebApplicationInitializer {

    private static final String DISPATCH_MAPPING_URL = "*.html";
    private static final String JERSEY_MAPPING_URL = "/rest/*";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Tell jersey-spring3 the context is already initialized
        servletContext.setInitParameter("contextConfigLocation", "NOTNULL");

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new RequestContextListener());

        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(MvcConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(DISPATCH_MAPPING_URL);

        ServletRegistration.Dynamic jerseyServlet = servletContext.addServlet("jersey-servlet",
                new ServletContainer());
        jerseyServlet.setInitParameter("javax.ws.rs.Application", "com.rts.config.RestConfig");
        jerseyServlet.setLoadOnStartup(1);
        jerseyServlet.addMapping(JERSEY_MAPPING_URL);
    }
}
