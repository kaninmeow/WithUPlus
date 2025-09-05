package com.withu;

import ch.qos.logback.core.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.util.ArrayList;

@SpringBootTest
class WithUPlusApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
	}

}
