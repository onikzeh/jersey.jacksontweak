package au.com.ko.samples.json.prov;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper defaultObjectMapper;

    public CustomObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.INDENT_OUTPUT, true);

        //If enabled, then DeserializationFeature.UNWRAP_ROOT_VALUE should be used in client
        // Or register this provider in ClientBuilder
        //om.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //If a Sensitive custom type is used... "nonMasking()" is required, if not "SensitiveValues" will be handled like normal objects
        //om.registerModule(SensitiveValueModule.nonMasking());

        return om;
    }
}