package util.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/12
 * Time: 17:08
 **/
public class StringAdapter implements JsonSerializer<String>,
        JsonDeserializer<String> {
    public JsonElement serialize(String src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        if (null == src) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src);
        }
    }
    public String deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}