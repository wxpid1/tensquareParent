package com.tensquare.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务启动
 * @author admin
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication  {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
