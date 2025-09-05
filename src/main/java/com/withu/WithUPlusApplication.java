package com.withu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@MapperScan("com.withu.mapper")
public class WithUPlusApplication {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		SpringApplication.run(WithUPlusApplication.class, args);
	}
}
