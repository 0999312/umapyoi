package net.tracen.umapyoi.registry.factors;

public enum FactorType {
    STATUS(5), UNIQUE(1), EXTRASTATUS(3), OTHER(5);

	private final int maxLevel;
	
	private FactorType(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
    @Override
    public String toString() {
        return name().toLowerCase();
    }

	public int getMaxLevel() {
		return maxLevel;
	}
}
