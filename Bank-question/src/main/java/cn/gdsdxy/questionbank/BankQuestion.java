package cn.gdsdxy.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//关闭权限管理
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
public class BankQuestion {

    public static void main(String[] args) {
        SpringApplication.run(BankQuestion.class, args);
    }

}

