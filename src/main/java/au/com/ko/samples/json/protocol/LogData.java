package au.com.ko.samples.json.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;
import java.util.Map;

@JsonRootName("log")
public class LogData {

	private final String id;
	private final Map<String, String> data;
	private final Date date;


	@JsonCreator
	public LogData(@JsonProperty("id") String id,
				   @JsonProperty("data") Map<String, String> data) {
		this.id = id;
		this.data = data;
		this.date = new Date();
	}

	public String getId() {
		return id;
	}

	public Map<String, String> getData() {
		return data;
	}

	public Date getDate() {
		return date;
	}
}
