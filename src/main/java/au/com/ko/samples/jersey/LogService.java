package au.com.ko.samples.jersey;

import au.com.ko.samples.json.protocol.LogData;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LogService {

	private final Map<String, String> data = new HashMap<>(3);
	private final LogData logData;

	public LogService() {
		data.put("key2", "value 2");
		data.put("key1", "value 1");
		data.put("key3", "value 3");

		logData = new LogData("Sample ID",data);
	}



	@POST
	@Path("log")
	public Response log(LogData log) {

		return Response.ok().entity(log).build();
	}

	@GET
	@Path("/log")
	public LogData produceJSON() {
		// Our custom Jackson mapper added to Jersey is used to convert 'logData' to JSON.
		return logData;
	}
}

