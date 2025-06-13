package Caprish.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class JsonConfig {

    private final String jwtSecret;

    public JsonConfig() throws IOException {
        InputStream is = new ClassPathResource("credentials/config.json")
                .getInputStream();
        JsonNode root = new ObjectMapper().readTree(is);
        this.jwtSecret = root.path("jwt").path("secret").asText();
    }

    @Bean
    public String jwtSecret() {
        return jwtSecret;
    }
}