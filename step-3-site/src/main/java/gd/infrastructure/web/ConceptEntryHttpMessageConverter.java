package gd.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.domain.entities.entity.ConceptEntry;
import gd.domain.entities.Entities;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

public class ConceptEntryHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    // SERVICES
    @Autowired
    private Entities conceptRepository;

    // OVERRIDED CONSTRUCTORS
    public ConceptEntryHttpMessageConverter() {
        super();
    }

    public ConceptEntryHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    // OVERRIDED READ
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return type == ConceptEntry.class;
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // get type from path param
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Map<String, String> pathParams = (Map<String, String>) attributes.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

        // load the type
        String targetType = pathParams.get("typeUid");
        Class<? extends ConceptEntry> targetClass = conceptRepository.getConceptTypeThrowExceptionIfNotFound(targetType).getTargetClass();

        // read the json
        return super.read(targetClass, contextClass, inputMessage);
    }

}
