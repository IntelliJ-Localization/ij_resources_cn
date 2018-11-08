package translation.main;

import com.google.gson.reflect.TypeToken;
import translation.bo.LanguageResource;
import translation.bo.ResourceFile;
import translation.bo.Resources;
import translation.util.JsonUtils;
import translation.util.PropertiesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static translation.util.PropertiesUtils.buildTranslationMap;
import static translation.util.PropertiesUtils.saveObjToJson;

/**
 * @author jianyun.zhao
 * Create at 2018/11/06
 */
public class LocalizationMain {


    private static String ALL_JSON_FILE_NAME = "all.json";
    private static String DIFF_JSON_FILE_NAME = "diff.json";
    private static String CHS_JSON_FILE_NAME = "chs.json";
    private static String EN_JSON_FILE_NAME = "en.json";

    public static void main(String[] args) {
        String resourcePath = "src/main/resources";
        String enResourcePath = "/Users/t/workspace/idea_l10n/2018.2.5/resources_en";
        String storagePath = "/Users/t/workspace/idea_l10n/2018.2.5/test";
        Path cnPath = Paths.get(resourcePath);

        Path enPath = Paths.get(enResourcePath);
//
//        generateResources(cnPath, enPath, cnPath);


        generateResourceJar(resourcePath, cnPath);
    }

    private static void generateResourceJar(String savePath, Path resouecePath) {
        Resources resources = JsonUtils.fromJson(Paths.get(resouecePath.toString(), ALL_JSON_FILE_NAME), Resources.class);

        if (resources == null) {
            throw new IllegalArgumentException("资源文件为空");
        }
        Path diffPath = Paths.get(resouecePath.toString(), DIFF_JSON_FILE_NAME);
        if (Files.exists(diffPath)) {
            List<ResourceFile> diff = JsonUtils.fromJson(Paths.get(resouecePath.toString(), DIFF_JSON_FILE_NAME), new TypeToken<List<ResourceFile>>() {
            }.getType());
            resources.setDiff(diff);
        }
        generateFiles(Paths.get(savePath), resources);
    }


    public static void generateResources(Path cnPath, Path enPath, Path stroageDir) throws IOException {
        Map<String, Properties> cnProperties = buildTranslationMap(cnPath);
        Map<String, Properties> enProperties = buildTranslationMap(enPath);
        Resources resources = buildResources(cnProperties, enProperties);


        saveObjToJson(Paths.get(stroageDir.toString(), CHS_JSON_FILE_NAME), cnProperties);
        saveObjToJson(Paths.get(stroageDir.toString(), EN_JSON_FILE_NAME), enProperties);

        saveObjToJson(Paths.get(stroageDir.toString(), ALL_JSON_FILE_NAME), resources);
        saveObjToJson(Paths.get(stroageDir.toString(), DIFF_JSON_FILE_NAME), resources.getDiff());
    }

    private static void generateFiles(Path storagePath, Resources resources) {

        List<ResourceFile> resourceFiles = resources.getResourceFiles();
        List<ResourceFile> diff = resources.getDiff();

        Map<String, ResourceFile> collect = resourceFiles.stream().collect(Collectors.toMap(ResourceFile::getPath, Function.identity()));
        Map<String, ResourceFile> diffMap = diff.stream().collect(Collectors.toMap(ResourceFile::getPath, Function.identity()));

        collect.forEach((k, v) -> {
            ResourceFile resourceFile = diffMap.get(k);
            if (resourceFile == null || resourceFile.getProps() == null || resourceFile.getProps().isEmpty()) {
                return;
            }
            Set<LanguageResource> languageResources = new LinkedHashSet<>(resourceFile.getProps());
            languageResources.addAll(v.getProps());

            v.setProps(new ArrayList<>(languageResources));
        });

        resourceFiles.forEach(r -> {
            Path path = Paths.get(storagePath.toString(), r.getPath());

            checkDirOrCreate(path);

            Properties properties = new Properties();
            if (r.getProps() != null) {
                Map<String, String> stringMap = r.getProps().stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(LanguageResource::getKey, l -> {
                            if (isBlank(l.getCnValue())) {
                                return l.getEnValue();
                            }
                            return l.getCnValue();
                        }));
                properties.putAll(stringMap);
            }
            PropertiesUtils.saveProperties(path, properties);
        });


    }

    private static void checkDirOrCreate(Path path) {
        if (Files.notExists(path.getParent())) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Resources buildResources(Map<String, Properties> cnProperties, Map<String, Properties> enProperties) {
        List<ResourceFile> resourceFileList = new ArrayList<>();
        List<ResourceFile> diff = new ArrayList<>();


        enProperties.forEach((filepath, enProp) -> {
            Properties cnProp = cnProperties.get(filepath);

            if (cnProp == null) {
                System.out.println("文件 " + filepath + "不存在");
                return;
            }

            ResourceFile resourceFile = new ResourceFile();
            resourceFile.setPath(filepath);
            resourceFile.setName(Paths.get(filepath).getFileName().toString());
            resourceFileList.add(resourceFile);

            ResourceFile diffFile = new ResourceFile();
            diffFile.setPath(filepath);
            diffFile.setName(Paths.get(filepath).getFileName().toString());


            enProp.stringPropertyNames()
                    .forEach(key -> {
                        String cnValue = cnProp.getProperty(key);
                        String enValue = enProp.getProperty(key);

                        LanguageResource languageResource = new LanguageResource();
                        languageResource.setKey(key);
                        languageResource.setEnValue(enValue);
                        languageResource.setCnValue(cnValue);
                        resourceFile.addProp(languageResource);

                        if (isBlank(cnValue) || cnValue.equals(enValue)) {
                            diffFile.addProp(languageResource);
                        }
                    });
            if (diffFile.getProps() == null || diffFile.getProps().isEmpty()) {
                return;
            }
            diff.add(diffFile);
        });

        Resources resources = new Resources();
        resources.setVersion("2018.2.5");
        resources.setDiff(diff);
        resources.setResourceFiles(resourceFileList);
        return resources;
    }


    private static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }


}
