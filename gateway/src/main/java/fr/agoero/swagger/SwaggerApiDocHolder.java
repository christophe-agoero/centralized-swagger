package fr.agoero.swagger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwaggerApiDocHolder {

    private static final ConcurrentHashMap<String, String> API_DOC_MAP = new ConcurrentHashMap<>();

    static void putApiDoc(String serviceName, String apiDoc) {
        API_DOC_MAP.put(serviceName, apiDoc);
    }

    static void clearApiDoc() {
        API_DOC_MAP.clear();
    }

    public static String getApiDoc(String serviceName) {
        return API_DOC_MAP.get(serviceName);
    }

    public static Set<String> getServiceNameSet() {
        return API_DOC_MAP.keySet();
    }
}
