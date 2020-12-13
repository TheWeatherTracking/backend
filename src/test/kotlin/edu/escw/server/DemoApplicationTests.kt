package edu.escw.server

import edu.escw.server.model.Device
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {

	@Test
	fun contextLoads() {
		val device: Device = Device();
	}

}
