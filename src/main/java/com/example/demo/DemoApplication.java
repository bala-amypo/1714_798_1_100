// package com.example.demo;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
// public class DemoApplication {
    
//     public static void main(String[] args) {
//         SpringApplication.run(DemoApplication.class, args);
//     }
// }


// package com.example.demo;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(
// 	 exclude = {
//         org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//     }
// )
// public class DemoApplication {

// 	public static void main(String[] args) {
// 		SpringApplication.run(DemoApplication.class, args);
// 	}

// }
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}