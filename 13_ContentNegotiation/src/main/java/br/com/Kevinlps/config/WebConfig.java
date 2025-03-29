package br.com.Kevinlps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        //via EXTENSION. http://localhost:8080/api/person/v1/1.xml or http://localhost:8080/api/person/v1/1.JSON Deprecated on Spring boot 2.6
/*        configurer.favorParameter(true)
                .parameterName("mediaType")
                .ignoreAcceptHeader(true)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json",MediaType.APPLICATION_JSON)
                .mediaType("xml",MediaType.APPLICATION_XML);
    }

 */
        //header param
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json",MediaType.APPLICATION_JSON)
                .mediaType("xml",MediaType.APPLICATION_XML)
                .mediaType("yaml",MediaType.APPLICATION_YAML);

    }
}
