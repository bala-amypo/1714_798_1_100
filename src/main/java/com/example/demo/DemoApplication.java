package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import com.example.demo.servlet.HealthServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    public ServletRegistrationBean<HealthServlet> simpleStatusServlet() {
        return new ServletRegistrationBean<>(new HealthServlet(), "/health");
    }
    
    // Optional: Create a simple redirect servlet for the root path
    @Bean
    public ServletRegistrationBean<RootRedirectServlet> rootRedirectServlet() {
        return new ServletRegistrationBean<>(new RootRedirectServlet(), "/");
    }
    
    @WebServlet
    public static class RootRedirectServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.sendRedirect("/swagger-ui/index.html");
        }
    }
}