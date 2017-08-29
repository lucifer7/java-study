package util.json;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**<b>BASED ON GSON</b>
 * User: Tony
 * Date: 11-8-15
 */
public class JsonUtils {
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static String toNoFormatJson(Object obj) {
        return noFormatGson.toJson(obj);
    }

    public static String toNoFormatJson(Object obj, Type type) {
        return noFormatGson.toJson(obj, type);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toPrettyJsonFromStr(String originalStr){
        String prettyJsonStr = null;
        if(StringUtils.isNotBlank(originalStr)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(originalStr);
            prettyJsonStr = gson.toJson(je);
        }
        return prettyJsonStr;
    }

    final static String pattern = "yyyy-MM-dd HH:mm:ss";

    static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final String asString = json.getAsString();
            if (asString != null) {
                try {
                    return new Date();  //TODO: tackle Date conversion
                    //return DateTimeUtils.parse(asString, pattern);
                } catch (Exception ignored) {

                }
            }

            return null;
        }
    };

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Calendar.class, new UtilCalendarSerializer())
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(GregorianCalendar.class, new UtilCalendarSerializer())
            .setDateFormat(DateFormat.LONG).setPrettyPrinting()
            .registerTypeAdapter(Date.class, deser)
            .registerTypeAdapter(String.class, new StringConverter())
            .setDateFormat(pattern)
            .create();

    private static Gson noFormatGson = new GsonBuilder()
            .registerTypeAdapter(Calendar.class, new UtilCalendarSerializer())
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(GregorianCalendar.class, new UtilCalendarSerializer())
            .setDateFormat(DateFormat.LONG)
            .registerTypeAdapter(Date.class, deser)
            .registerTypeAdapter(String.class, new StringConverter())
            .setDateFormat(pattern)
            .create();

    private static class UtilDateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
        @Override
        public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }
    }

    private static class UtilCalendarSerializer implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
        @Override
        public JsonElement serialize(Calendar cal, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(cal.getTimeInMillis());
        }

        @Override
        public Calendar deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(element.getAsJsonPrimitive().getAsLong());
            return cal;
        }
    }

    private static class StringConverter implements JsonSerializer<String>,
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
}
