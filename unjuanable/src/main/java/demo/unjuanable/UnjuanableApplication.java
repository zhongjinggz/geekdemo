package demo.unjuanable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("demo.unjuanable.adapter.driven.persistence")
@SpringBootApplication
public class UnjuanableApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnjuanableApplication.class, args);
    }

}
