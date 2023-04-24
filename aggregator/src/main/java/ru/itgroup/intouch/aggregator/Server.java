package ru.itgroup.intouch.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"ru.itgroup.intouch.client"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
