package com.enndfp.shortlink.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Enndfp
 */
@SpringBootApplication
@MapperScan("com.enndfp.shortlink.project.dao.mapper")
public class ShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class,args);
    }
}
