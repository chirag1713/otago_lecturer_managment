/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.config;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.test.AppConfigLocal;

/**
 *
 * @author omm
 */
public class WebAppInitializer implements WebApplicationInitializer {

    // @Override
    // public void onStartup(ServletContext sc) throws ServletException {
    // ServletRegistration.Dynamic registration = sc.addServlet("dispatcher",
    // new DispatcherServlet());
    // registration.setLoadOnStartup(1);
    // registration.addMapping("/springAssignment/*");
    // }

    private static ApplicationContext applicationContext = null;

    private static ServletContext servletContext = null;

    static {
        /* String environmentMode = System.getProperty(Constants.ENVIRONMENT_MODE);
         if (environmentMode == null) {
             System.out.println("EnvironmentMode is not set in the jvm argument. system exit.\n1. Eclipse: Project > Right Click > Run As > Run Configurations > Apache Tomcat > Tomcat XX > (X)= Argument > VM Arguments > Add argument -DEnvironmentMode=DEV\n2. Netbeans: Servers > Tomcat XXX > Platform > VM Options > Add argument -DEnvironmentMode=DEV");
             System.exit(0);
         } else if (!environmentMode.equals(Constants.ENVIRONMENT_DEV) && !environmentMode.equals(Constants.ENVIRONMENT_TEST) && !environmentMode.equals(Constants.ENVIRONMENT_PROD)) {
             System.out.println("Invalid EnvironmentMode set in the jvm argument: " + System.getProperty(Constants.ENVIRONMENT_MODE) + ". system exit.");
             System.exit(0);
         } else {*/
        // Registering the class that incorporates the annotated
        // DispatcherServlet configuration of spring
        if (System.getProperty("isLocal") == null) {
            System.out.println("*****Initializing Web Application context TheaterWeb");
            applicationContext = new AnnotationConfigWebApplicationContext();
            ((AnnotationConfigRegistry) applicationContext).register(AppConfig.class);
        } /*else {
            System.out.println("*****Initializing Local Application context TheaterWeb");
            applicationContext = new AnnotationConfigApplicationContext();
            ((AnnotationConfigRegistry) applicationContext).register(AppConfigLocal.class);
          }*/
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public void onStartup(ServletContext servletContext) throws ServletException {

        this.servletContext = servletContext;

        System.out.println("*****onStartup app dir: " + servletContext.getRealPath(File.separator) + ", user.dir: " + System.getProperty("user.dir") + ", context path: " + servletContext.getContextPath());
        // Adding the listener for the rootContext
        servletContext.addListener(new ContextLoaderListener((WebApplicationContext) applicationContext));
        // Registering the dispatcher servlet mappings.
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet((WebApplicationContext) applicationContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    public void doSomething() {
        System.out.println("****App Config Done");
    }

}
