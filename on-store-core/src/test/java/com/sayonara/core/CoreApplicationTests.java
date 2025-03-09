package com.sayonara.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "interval_in_cron=0 0/5 * * * ?")
class CoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
