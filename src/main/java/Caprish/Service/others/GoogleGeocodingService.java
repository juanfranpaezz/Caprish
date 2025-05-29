package Caprish.Service.others;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@Service
public class GoogleGeocodingService {

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate= new RestTemplate();

    public boolean validateAddress(String inputAddress) {
        try {
            URI uri = UriComponentsBuilder.fromUriString("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", inputAddress)
                    .queryParam("key", apiKey)
                    .build()
                    .toUri();

            Map response = restTemplate.getForObject(uri, Map.class);
            if (response == null || !"OK".equals(response.get("status"))) {
                return false;
            }

            var results = (java.util.List<Map>) response.get("results");
            if (results.isEmpty()) return false;

            Map firstResult = results.get(0);

            // 1. Verificar partial_match
            Boolean partialMatch = (Boolean) firstResult.get("partial_match");
            if (partialMatch != null && partialMatch) {
                return false;
            }

            // 2. Verificar location_type
            Map geometry = (Map) firstResult.get("geometry");
            String locationType = (String) geometry.get("location_type");
            if (!"ROOFTOP".equals(locationType)) {
                return false;
            }

            // 3. (Opcional) Comparar con formatted_address
            String formatted = (String) firstResult.get("formatted_address");
            if (formatted == null || !formatted.toLowerCase().contains(inputAddress.split(",")[0].toLowerCase())) {
                return false;
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al validar la dirección con Google Maps", e);
        }
    }
}
