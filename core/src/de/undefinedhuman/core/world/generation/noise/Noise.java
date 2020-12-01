package de.undefinedhuman.core.world.generation.noise;

import java.util.Random;

public class Noise {

    private Random random = new Random();

    private int seed, octaves;
    private float amplitude, roughness;

    public Noise(int octaves, float amplitude, float roughness) {
        this(new Random().nextInt(10000), octaves, amplitude, roughness);
    }

    public Noise(int seed, int octaves, float amplitude, float roughness) {
        this.seed = seed;
        this.octaves = octaves;
        this.amplitude = amplitude;
        this.roughness = roughness;
    }

    public float fractal(float x, float y) {
        return this.fractal(x, y, seed, octaves, amplitude, roughness);
    }

    public float fractal(float x, float y, int seed, int octaves, float amplitude, float roughness) {
        float value = 0;
        float d = (float) Math.pow(2, octaves - 1);
        for (int i = 0; i < octaves; i++) {
            float freq = (float) (Math.pow(2, i) / d), amp = (float) Math.pow(roughness, i) * amplitude;
            value += getInterpolatedNoise(x * freq, y * freq, seed) * amp;
        }
        return Math.abs(value);
    }

    public float getInterpolatedNoise(float x, float y, int seed) {
        int intX = (int) x, intY = (int) y;
        float fracX = x - intX, fracY = y - intY;
        float v1 = getSmoothNoise(intX, intY, seed), v2 = getSmoothNoise(intX + 1, intY, seed), v3 = getSmoothNoise(intX, intY + 1, seed), v4 = getSmoothNoise(intX + 1, intY + 1, seed);
        float i1 = cosInterpolation(v1, v2, fracX), i2 = cosInterpolation(v3, v4, fracX);
        return cosInterpolation(i1, i2, fracY);
    }

    private float getSmoothNoise(int x, int y, int seed) {
        float corners = (getNoise(x - 1, y - 1, seed) + getNoise(x + 1, y - 1, seed) + getNoise(x - 1, y + 1, seed) + getNoise(x + 1, y + 1, seed)) / 16f;
        float sides = (getNoise(x - 1, y, seed) + getNoise(x + 1, y, seed) + getNoise(x, y - 1, seed) + getNoise(x, y + 1, seed)) / 8f;
        float center = getNoise(x, y, seed) / 4f;
        return corners + sides + center;
    }

    private float getNoise(int x, int y, int seed) {
        random.setSeed(x * 49632 + y * 325176 + seed);
        return random.nextFloat() * 2f - 1f;
    }

    public float gradient(float x, float y, float maxY) {
        float value = y / maxY;
        value += scale(0.5f, 0, fractal(x, y));
        return linInterpolation(0,1, value);
    }

    public boolean select(float threshold, float value) { return value >= threshold; }
    public boolean selectDiff(float threshold, float value) { return value <= threshold; }
    public float scale(float scale, float offset, float value) {
        return value * scale + offset;
    }
    private float cosInterpolation(float a, float b, float blend) {
        float c = (float) ((1f - Math.cos((blend * Math.PI))) * 0.5f);
        return a * (1 - c) + b * c;
    }
    public float linInterpolation(float a, float b, float v) {
        return (a * (b - v) + b * v);
    }

    public Noise setSeed(int seed) {
        this.seed = seed;
        return this;
    }

}
