package gd.infrastructure.log;

import org.slf4j.LoggerFactory;

public class LogProducer {

    public static org.slf4j.Logger getLogger(Class c) {
        return LoggerFactory.getLogger(c);
    }

}
