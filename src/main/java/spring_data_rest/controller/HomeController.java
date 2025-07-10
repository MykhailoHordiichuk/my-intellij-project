package spring_data_rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {
        logger.info("GET / - home() called");
        return "Welcome to Online Language School API. Swagger: /swagger-ui/index.html";
    }
}
