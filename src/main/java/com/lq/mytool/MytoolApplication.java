package com.lq.mytool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.lq.mytool.mapper")
public class MytoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytoolApplication.class, args );
    }

}
