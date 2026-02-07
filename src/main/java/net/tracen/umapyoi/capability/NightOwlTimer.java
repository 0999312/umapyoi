package net.tracen.umapyoi.capability;

public class NightOwlTimer implements ILevelTimer {
    private long counter = 0;

    @Override
    public void tick() {
        counter++;
    }

    @Override
    public void reset() {
        counter = 0;
    }

    @Override
    public long getCurrentTick() {
        return counter;
    }
}
