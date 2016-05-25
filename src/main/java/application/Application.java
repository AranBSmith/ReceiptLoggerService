package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Class used to initiate the Web Service, it identifies where to search for the
 * relevant classes using the \@ComponentScan annotation.
 * 
 * This is a test for the demo.
 * 
 * @author Aran
 *
 */
@Configuration
@ComponentScan({"controllers", "application", "services", "dao"})
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<Application> applicationClass = Application.class;
}