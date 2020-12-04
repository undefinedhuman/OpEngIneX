package de.undefinedhuman.core.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {

    public static float cosInterpolate(float a, float b, float blend) {
        double ft = blend * 3.141592653589793D;
        float f = (float) ((1.0D - Math.cos(ft)) * 0.5D);
        return a * (1.0F - f) + b * f;
    }

    public static float baryCentricInterpolation(Vector3f a, Vector3f b, Vector3f c, Vector2f p) {
        float det = 1.0f / ((b.z - c.z) * (a.x - c.x) + (c.x - b.x) * (a.z - c.z));
        float v = (b.z - c.z) * (p.x - c.x) + (c.x - b.x) * (p.y - c.z) * det;
        float w = (c.z - a.z) * (p.x - c.x) + (a.x - c.x) * (p.y - c.z) * det;
        float u = 1 - v - w;
        return v * a.y + w * b.y + u * c.y;
    }

    public static Vector2f rotateVector2(Vector2f point, Vector2f center, float angle) {
        float s = (float) Math.sin(angle), c = (float) Math.cos(angle);
        Vector2f temp = new Vector2f(point).sub(center);
        Vector2f newPoint = new Vector2f(temp.x * c - temp.y * s, temp.x * s + temp.y * c);
        point = new Vector2f(newPoint).add(center);
        return point;
    }

    public static float lerp(float a, float b, float f) {
        return a * (1 - f) + (b * f);
    }

    public static int floor(float f) {
        if (f >= 0) return (int) f;
        else return (int) f - 1;
    }

    public static float mix(float a, float b, float f) {
        return a * (1 - f) + (b * f);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static boolean isInRange(int val, int min, int max) {
        return val >= min && val <= max;
    }

    public static boolean isInRange(float val, float min, float max) {
        return val >= min && val <= max;
    }

    public static int isEqual(int i, int j) {
        return i == j ? 1 : 0;
    }

}
