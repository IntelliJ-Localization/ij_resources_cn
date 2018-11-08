package translation.util;

import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * @author jianyun.zhao
 * Create at 2018/11/07
 */
public class PropertiesUtils {

    private static BiPredicate<Path, BasicFileAttributes> matcher = (t, u) -> t.getFileName().toString().endsWith("properties");

    public static void saveProperties(Path path, Properties properties) {

        try (FileWriter fileWriter = new FileWriter(path.toFile())) {
            properties.store(fileWriter, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveObjToJson(Path path, Object properties) throws IOException {
        String json = JsonUtils.toJson(properties);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.write(path, json.getBytes("UTF-8"));
    }

    public static Map<String, Properties> readTranslationMap(Path store) throws IOException {
        String json = String.join("", Files.readAllLines(store));
        return JsonUtils.fromJson(json, new TypeToken<Map<String, Properties>>() {
        }.getType());
    }

    public static Map<String, Properties> buildTranslationMap(Path path) throws IOException {
        return Files.find(path, 20, matcher)
            .collect(Collectors.toMap(path1 -> path1.toString().replace(path.toString(), ""), PropertiesUtils::loadProperties));
    }

    public static Properties loadProperties(Path path) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
