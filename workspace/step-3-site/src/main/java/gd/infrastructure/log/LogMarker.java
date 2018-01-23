package gd.infrastructure.log;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogMarker {

    public static final Marker API = MarkerFactory.getMarker("API");
    public static final Marker DB = MarkerFactory.getMarker("DB");
    public static final Marker INIT = MarkerFactory.getMarker("INI");
    public static final Marker DOMAIN = MarkerFactory.getMarker("DMN");
    public static final Marker PROFILE = MarkerFactory.getMarker("PRF");
    public static final Marker REQUEST = MarkerFactory.getMarker("REQ");
    public static final Marker TEST = MarkerFactory.getMarker("TST");
}
