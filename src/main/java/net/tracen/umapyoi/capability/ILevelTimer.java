package net.tracen.umapyoi.capability;

public interface ILevelTimer {
    void tick();
    void reset();
    long getCurrentTick();
}
