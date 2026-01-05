package net.tracen.umapyoi.utils;

public enum Aptitude {
    G(0.7), F(0.75), E(0.8), D(0.85), C(0.9), B(0.95), A(1), S(1.05);
    public final double factor;
    Aptitude(double factor) {
        this.factor = factor;
    }
}
