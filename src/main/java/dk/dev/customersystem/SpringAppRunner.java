package dk.dev.customersystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class SpringAppRunner {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SpringAppRunner.class);
        if (checkProfile("console")) {
            app.setWebApplicationType(WebApplicationType.NONE);
        }
        app.run(args);
    }

    private static boolean checkProfile(String profile) {
        try {
            ClassPathResource resource = new ClassPathResource("application.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            String activeProfile = properties.getProperty("spring.profiles.active");
            return activeProfile != null && activeProfile.contains(profile);
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
