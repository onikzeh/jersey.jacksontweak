package au.com.ko.samples.unittest;

import au.com.ko.samples.json.protocol.LogData;
import au.com.ko.samples.json.prov.CustomObjectMapperProvider;
import au.com.ko.samples.unittest.support.TestServer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static junit.framework.TestCase.assertNotNull;

@RunWith(JUnit4.class)
public class LogTest {

	private static TestServer testServer;
	private static WebTarget target;

	@BeforeClass
	public static void startServer() throws IOException {
		Properties config = new Properties();
		config.load(LogTest.class.getResourceAsStream("/config.properties"));

		String contextPath = config.getProperty("test.server.contextPath");

		URI baseURI = UriBuilder.fromUri("http://localhost/" + contextPath)
				.port(Integer.parseInt(config.getProperty("test.server.port"))).build();

		testServer = new TestServer(baseURI);
		testServer.start();

		ClientBuilder clientBuilder = ClientBuilder.newBuilder()
				.register(JacksonFeature.class);

		Client client = clientBuilder.build();
		target = client.target(baseURI);
	}

	@AfterClass
	public static void stopServer() {
		if (testServer != null) {
			testServer.stop();
		}
	}

	@Test
	public void testGetLog() throws IOException {
		String receivedJSON = target
				.path("log")
				.request()
				.accept(APPLICATION_JSON)
				.get(String.class);

		System.out.println(receivedJSON);
		assertNotNull("Response: ", receivedJSON);
	}

	@Test
	public void testPostLog() throws IOException {
		Map<String, String> data = new HashMap<>(3);
		LogData logData;

		data.put("key2", "value 2");
		data.put("key1", "value 1");
		data.put("key3", "value 3");

		logData = new LogData("Sample ID", data);

		Response response = target
				.path("log")
				.request(APPLICATION_JSON)
				.post(Entity.entity(logData, MediaType.APPLICATION_JSON), Response.class);

		LogData receivedData = response.readEntity(LogData.class);
		assertNotNull("Response: ", receivedData);
	}

}
