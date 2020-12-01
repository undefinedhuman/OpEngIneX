package de.undefinedhuman.core.world.generation;

@FunctionalInterface
public interface HeightGenerator {
    float generateHeight(int x, int z);
}
