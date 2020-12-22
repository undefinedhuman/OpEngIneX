package de.undefinedhuman.engine.world.generation;

@FunctionalInterface
public interface HeightGenerator {
    float generateHeight(int x, int z);
}
