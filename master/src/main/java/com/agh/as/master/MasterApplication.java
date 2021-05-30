package com.agh.as.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class MasterApplication {

    public static void main(String[] args) {
//        BlockHound.install();
        SpringApplication.run(MasterApplication.class, args);
    }

}
