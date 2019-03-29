package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import spark.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final Gson gson = new Gson();


    public static String objectToJson(Object model) {
        return gson.toJson(model);
    }


    public static Map<String, Object> requestToMap(Request r) throws Exception {
        String body = r.body();
        if (body.length() == 0) {
            return new TreeMap<>();
        }
        return mapper.readValue(body, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * Method to deserialize String content to Object.
     */
    public <T> T mapTo(String str, Class<T> tClass) throws Exception {
        return mapper.readValue(str, tClass);
    }

    public static <T> T getJsonBodyParameter(Request request, String parameter) {
        try {
            return (T) requestToMap(request).get(parameter);
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap<String, String> formPayloadToMap(String req){
        HashMap<String, String> map = new HashMap<>();

        try {
            String[] expressions = req.trim().split("\r\n");
            for (String expression : expressions) {
                String[] keyValue = expression.trim().split("=",2);
                map.put(keyValue[0], keyValue[1]);
            }
        } catch (Exception e) {
            System.out.println("Malformed payload");
            e.printStackTrace();
        }

        return map;
    }

}