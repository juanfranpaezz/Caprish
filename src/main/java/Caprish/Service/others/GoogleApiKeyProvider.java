package Caprish.Service.others;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class GoogleApiKeyProvider {

    private final String apiKey;

    public GoogleApiKeyProvider() {
        this.apiKey = loadApiKeyFromJson();
    }

    private String loadApiKeyFromJson() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("credentials/google-api-key.json");
            if (inputStream == null) throw new RuntimeException("Archivo google-api-key.json no encontrado");

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> jsonMap = mapper.readValue(inputStream, Map.class);
            String key = jsonMap.get("googleApiKey");

            if (key == null || key.isBlank()) {
                throw new RuntimeException("Clave de API no encontrada en el JSON");
            }

            return key;
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la clave de la API desde el JSON", e);
        }
    }

    public String getApiKey() {
        return apiKey;
    }
}
