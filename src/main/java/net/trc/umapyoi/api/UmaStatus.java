package net.trc.umapyoi.api;

public enum UmaStatus {
    SPEED("speed"),
    STAMINA("stamina"),
    STRENGTH("strength"),
    MENTALITY("mentality"),
    WISDOM("wisdom"),
    MAX_SPEED("max_speed"),
    MAX_STAMINA("max_stamina"),
    MAX_STRENGTH("max_strength"),
    MAX_MENTALITY("max_mentality"),
    MAX_WISDOM("max_wisdom");
    private final String name;
    private UmaStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
