package top.mowang.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
@MapperScan("top.mowang.security")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecuritySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecuritySpringBootApplication.class, args);
    }

}
