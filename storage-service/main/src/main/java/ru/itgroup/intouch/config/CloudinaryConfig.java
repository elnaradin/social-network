package ru.itgroup.intouch.config;

import com.cloudinary.Cloudinary;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
public class CloudinaryConfig {

    private Map<String, String> transformation;
    private Map<String, String> connection;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(connection);
    }
}
