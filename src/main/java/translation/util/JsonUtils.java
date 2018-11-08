package translation.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author jianyun.zhao
 * Create at 2018/11/07
 */
public class JsonUtils {

    private static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .serializeNulls()
        .create();

    public static <T> T fromJson(Path path, Type type) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
