package gd.infrastructure.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.context.request.RequestAttributes;

public class DummyRequestAttributes implements RequestAttributes {

    private final Map<String, Object> requestParameters = new HashMap<String, Object>();

    public DummyRequestAttributes() {
    }

    // =========================================================================
    // INTERFACE IMPLEMENTATION
    // =========================================================================
    @Override
    public Object getAttribute(String name, int scope) {
        return requestParameters.get(name);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        requestParameters.put(name, value);
    }

    @Override
    public void removeAttribute(String name, int scope) {
        requestParameters.remove(name);
    }

    @Override
    public String[] getAttributeNames(int scope) {
        String[] keys = new String[requestParameters.size()];
        requestParameters.keySet().toArray(keys);

        return keys;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        // do nothing
    }

    @Override
    public Object resolveReference(String key) {
        return requestParameters;
    }

    @Override
    public String getSessionId() {
        return "dummyRequest";
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }

}
