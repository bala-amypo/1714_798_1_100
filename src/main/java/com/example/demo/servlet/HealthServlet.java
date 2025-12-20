// src/main/java/com/example/demo/servlet/HealthServlet.java
package com.example.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/health")
public class HealthServlet extends HttpServlet {
    
    public HealthServlet() {
        // No-arg constructor
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        PrintWriter writer = response.getWriter();
        writer.write("Application is healthy");
        writer.flush();
    }
}