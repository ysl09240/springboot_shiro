package com.slin.forest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 初始程序
 *
 * @author yangsonglin
 * @create 2018-11-12 17:54
 **/
@ServletComponentScan  //配合@webFileter使用
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
