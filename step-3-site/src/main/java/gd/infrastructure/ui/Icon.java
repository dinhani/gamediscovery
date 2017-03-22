package gd.infrastructure.ui;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Icon {

    NONE(""),
    BALL("soccer"),
    BOOK("book"),
    BUILDING("building outline"),
    CAR("car"),
    CALENDAR("calendar"),
    CLOCK("clock"),
    CLOUD("cloud"),
    DOLLAR("dollar"),
    GAME("game"),
    ITEM("cubes"),
    MONSTER("bug"),
    MUSIC("music"),
    PERSON("male"),
    PLATFORM("server"),
    PUZZLE("puzzle"),
    QUOTE("quote left"),
    SMILE("smile"),
    STARTSHIP("space shuttle"),
    TAG("tag"),
    TREE("tree"),
    TROPHY("trophy"),
    VIDEOCAMERA("record"),
    WEAPON("bomb");

    private final String name;

    // =========================================================================
    // CONSTRUCTORS
    // =========================================================================
    private Icon(String name) {
        this.name = name;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================
    @JsonValue
    public String getName() {
        return name;
    }

}
