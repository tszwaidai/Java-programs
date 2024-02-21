package com.bookmanage.bms;

import com.bookmanage.bms.utils.PathUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//事务管理
@EnableTransactionManagement
//扫描接口
@MapperScan(value = "com.bookmanage.bms.mapper")
public class BookManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookManagerApplication.class, args);
    }

}
