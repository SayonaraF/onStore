import com.sayonara.api.ApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ApiApplication.class, properties = {"grpc.server.port=9091"})
class ApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
