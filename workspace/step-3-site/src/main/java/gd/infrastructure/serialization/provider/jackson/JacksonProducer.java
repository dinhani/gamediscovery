package gd.infrastructure.serialization.provider.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gd.infrastructure.enviroment.Environment;
import gd.infrastructure.steriotype.GDProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@GDProducer
public class JacksonProducer {

    @Autowired
    private Environment environment;

    @Bean
    public ObjectMapper objectMapper() {
        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

        // configuration
        if (environment.isDevelopment()) {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);

        return mapper;
    }

}
