package com.anilakdemir.auctionshortenedurl.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author anilakdemir
 */
@Configuration
public class SwaggerConfig{


    @Value("${application.title}")
    private String APP_TITLE;

    @Bean
    public OpenAPI customOpenAPI(){

        final String apiTitle = StringUtils.capitalize(APP_TITLE);

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(apiTitle)
                        .contact(new Contact().email("anilakd@hotmail.com").name("Anıl Akdemir"))
                        .description("Url Shortener Application")
                );
    }

}
