package net.tracen.umapyoi.registry.umadata;

public enum Motivations {
    PERFECT("perfect"), GOOD("good"), NORMAL("normal"), DOWN("down"), BAD("bad");

    private final String name;

    private Motivations(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Motivations getMotivation(String name) {
        for (Motivations motivation : Motivations.values()) {
            if (motivation.getName().equals(name)) {
                return motivation;
            }
        }
        return NORMAL;
    }
}
