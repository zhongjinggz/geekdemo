package demo.unjuanable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/*
 * To use swagger-ui 3.0.0:
 * url: http://localhost:8080/swagger-ui/index.html
 * add to application.yml:
 *   spring:
 *     mvc:
 *       pathmatch:
 *         matching-strategy: ANT_PATH_MATCHER
 */
@EnableOpenApi
@SpringBootApplication
public class UnjuanableApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnjuanableApplication.class, args);
    }

}
