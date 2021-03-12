package Server;

import com.google.gson.*;

public class Serializer {
    public static <T> String serialize(T object) {
        return (new Gson()).toJson(object);
    }
    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
    public static <T> T deserializeJsonElement(JsonElement value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
}
