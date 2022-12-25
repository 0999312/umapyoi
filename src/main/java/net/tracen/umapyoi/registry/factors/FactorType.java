package net.tracen.umapyoi.registry.factors;

public enum FactorType {
    STATUS, UNIQUE, OTHER;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
