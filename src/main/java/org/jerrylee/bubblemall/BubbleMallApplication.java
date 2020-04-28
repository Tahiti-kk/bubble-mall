package org.jerrylee.bubblemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.jerrylee.bubblemall.dao")
public class BubbleMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(BubbleMallApplication.class, args);
    }

}