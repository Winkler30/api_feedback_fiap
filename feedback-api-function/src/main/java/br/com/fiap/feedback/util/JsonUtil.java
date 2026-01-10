package br.com.fiap.feedback.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtil() {
    }

    public static <T> T read(String json, Class<T> type) throws Exception {
        return OBJECT_MAPPER.readValue(json, type);
    }

    public static String write(Object value) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(value);
    }
}
