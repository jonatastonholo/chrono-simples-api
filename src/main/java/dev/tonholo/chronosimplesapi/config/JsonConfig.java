package dev.tonholo.chronosimplesapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class JsonConfig {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
//        javaTimeModule.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));

        mapper.registerModule(javaTimeModule);
        mapper.registerModule(new Jdk8Module());
        // Hide null fields in serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Hide Optional.empty on serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        // Hide empty collections on serialization
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
