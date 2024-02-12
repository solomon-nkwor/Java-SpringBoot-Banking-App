package com.solomon.nkwor.solomon.bank.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.web.bind.annotation.RestController;

@RestController
@OpenAPIDefinition(info = @Info(
        title = "Banking Application",
        version = "1.0",
        description = "API Documentation for Banking Application",
        license = @License(name = "apache 2.0", url = "http://foo.bar"),
        contact = @Contact(url = "https://github.com/solomon-nkwor/Java-SpringBoot-Banking-App", name = "Solomon", email = "solomonnkwor6@gmail.com")
))
public class APIDocConfig {
}
