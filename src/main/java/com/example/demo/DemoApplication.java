// package com.example.demo;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class DemoApplication {
    
//     public static void main(String[] args) {
//         SpringApplication.run(DemoApplication.class, args);
//     }
// }


package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.servlet.HealthServlet;

@SpringBootApplication
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public ServletRegistrationBean<HealthServlet> healthServletRegistration() {
        ServletRegistrationBean<HealthServlet> registration = 
            new ServletRegistrationBean<>(new HealthServlet(), "/health");
        registration.setLoadOnStartup(1);
        return registration;
    }
}