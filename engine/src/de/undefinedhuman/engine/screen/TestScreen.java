package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.input.Keys;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.screen.Screen;
import de.undefinedhuman.core.world.generation.noise.Noise;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

public class TestScreen implements Screen {

    public static TestScreen instance;

    Vector3i[] VERTICES = new Vector3i[] {
            new Vector3i(0, 0, 0),
            new Vector3i(1, 0, 0),
            new Vector3i(1, 1, 0),
            new Vector3i(0, 1, 0),
            new Vector3i(0, 0, 1),
            new Vector3i(1, 0, 1),
            new Vector3i(1, 1, 1),
            new Vector3i(0, 1, 1),
    };

    Vector2f[] EDGES = new Vector2f[] {
            new Vector2f(0, 1),
            new Vector2f(1, 2),
            new Vector2f(2, 3),
            new Vector2f(3, 0),
            new Vector2f(4, 5),
            new Vector2f(5, 6),
            new Vector2f(6, 7),
            new Vector2f(7, 4),
            new Vector2f(0, 4),
            new Vector2f(1, 5),
            new Vector2f(2, 6),
            new Vector2f(3, 7),
    };

    ArrayList<Vector3f[]> cases = new ArrayList<>(Arrays.asList(
            new Vector3f[] {},
            new Vector3f[] {new Vector3f(8, 0, 3)},
            new Vector3f[] {new Vector3f(1, 0, 9)},
            new Vector3f[] {new Vector3f(8, 1, 3), new Vector3f(8, 9, 1)},
            new Vector3f[] {new Vector3f(10, 2, 1)},
            new Vector3f[] {new Vector3f(8, 0, 3), new Vector3f(1, 10, 2)},
            new Vector3f[] {new Vector3f(9, 2, 0), new Vector3f(9, 10, 2)},
            new Vector3f[] {new Vector3f(3, 8, 2), new Vector3f(2, 8, 10), new Vector3f(10, 8, 9)},
            new Vector3f[] {new Vector3f(3, 2, 11)},
            new Vector3f[] {new Vector3f(0, 2, 8), new Vector3f(2, 11, 8)},
            new Vector3f[] {new Vector3f(1, 0, 9), new Vector3f(2, 11, 3)},
            new Vector3f[] {new Vector3f(2, 9, 1), new Vector3f(11, 9, 2), new Vector3f(8, 9, 11)},
            new Vector3f[] {new Vector3f(3, 10, 11), new Vector3f(3, 1, 10)},
            new Vector3f[] {new Vector3f(1, 10, 0), new Vector3f(0, 10, 8), new Vector3f(8, 10, 11)},
            new Vector3f[] {new Vector3f(0, 11, 3), new Vector3f(9, 11, 0), new Vector3f(10, 11, 9)},
            new Vector3f[] {new Vector3f(8, 9, 11), new Vector3f(11, 9, 10)},
            new Vector3f[] {new Vector3f(7, 4, 8)},
            new Vector3f[] {new Vector3f(3, 7, 0), new Vector3f(7, 4, 0)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(9, 1, 0)},
            new Vector3f[] {new Vector3f(9, 1, 4), new Vector3f(4, 1, 7), new Vector3f(7, 1, 3)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(2, 1, 10)},
            new Vector3f[] {new Vector3f(4, 3, 7), new Vector3f(4, 0, 3), new Vector3f(2, 1, 10)},
            new Vector3f[] {new Vector3f(2, 0, 10), new Vector3f(0, 9, 10), new Vector3f(7, 4, 8)},
            new Vector3f[] {new Vector3f(9, 10, 4), new Vector3f(4, 10, 3), new Vector3f(3, 10, 2), new Vector3f(4, 3, 7)},
            new Vector3f[] {new Vector3f(4, 8, 7), new Vector3f(3, 2, 11)},
            new Vector3f[] {new Vector3f(7, 4, 11), new Vector3f(11, 4, 2), new Vector3f(2, 4, 0)},
            new Vector3f[] {new Vector3f(1, 0, 9), new Vector3f(2, 11, 3), new Vector3f(8, 7, 4)},
            new Vector3f[] {new Vector3f(2, 11, 1), new Vector3f(1, 11, 9), new Vector3f(9, 11, 7), new Vector3f(9, 7, 4)},
            new Vector3f[] {new Vector3f(10, 11, 1), new Vector3f(11, 3, 1), new Vector3f(4, 8, 7)},
            new Vector3f[] {new Vector3f(4, 0, 7), new Vector3f(7, 0, 10), new Vector3f(0, 1, 10), new Vector3f(7, 10, 11)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(0, 11, 3), new Vector3f(9, 11, 0), new Vector3f(10, 11, 9)},
            new Vector3f[] {new Vector3f(4, 11, 7), new Vector3f(9, 11, 4), new Vector3f(10, 11, 9)},
            new Vector3f[] {new Vector3f(9, 4, 5)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(0, 3, 8)},
            new Vector3f[] {new Vector3f(0, 5, 1), new Vector3f(0, 4, 5)},
            new Vector3f[] {new Vector3f(4, 3, 8), new Vector3f(5, 3, 4), new Vector3f(1, 3, 5)},
            new Vector3f[] {new Vector3f(5, 9, 4), new Vector3f(10, 2, 1)},
            new Vector3f[] {new Vector3f(8, 0, 3), new Vector3f(1, 10, 2), new Vector3f(4, 5, 9)},
            new Vector3f[] {new Vector3f(10, 4, 5), new Vector3f(2, 4, 10), new Vector3f(0, 4, 2)},
            new Vector3f[] {new Vector3f(3, 10, 2), new Vector3f(8, 10, 3), new Vector3f(5, 10, 8), new Vector3f(4, 5, 8)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(11, 3, 2)},
            new Vector3f[] {new Vector3f(11, 0, 2), new Vector3f(11, 8, 0), new Vector3f(9, 4, 5)},
            new Vector3f[] {new Vector3f(5, 1, 4), new Vector3f(1, 0, 4), new Vector3f(11, 3, 2)},
            new Vector3f[] {new Vector3f(5, 1, 4), new Vector3f(4, 1, 11), new Vector3f(1, 2, 11), new Vector3f(4, 11, 8)},
            new Vector3f[] {new Vector3f(3, 10, 11), new Vector3f(3, 1, 10), new Vector3f(5, 9, 4)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(1, 10, 0), new Vector3f(0, 10, 8), new Vector3f(8, 10, 11)},
            new Vector3f[] {new Vector3f(5, 0, 4), new Vector3f(11, 0, 5), new Vector3f(11, 3, 0), new Vector3f(10, 11, 5)},
            new Vector3f[] {new Vector3f(5, 10, 4), new Vector3f(4, 10, 8), new Vector3f(8, 10, 11)},
            new Vector3f[] {new Vector3f(9, 7, 5), new Vector3f(9, 8, 7)},
            new Vector3f[] {new Vector3f(0, 5, 9), new Vector3f(3, 5, 0), new Vector3f(7, 5, 3)},
            new Vector3f[] {new Vector3f(8, 7, 0), new Vector3f(0, 7, 1), new Vector3f(1, 7, 5)},
            new Vector3f[] {new Vector3f(7, 5, 3), new Vector3f(3, 5, 1)},
            new Vector3f[] {new Vector3f(7, 5, 8), new Vector3f(5, 9, 8), new Vector3f(2, 1, 10)},
            new Vector3f[] {new Vector3f(10, 2, 1), new Vector3f(0, 5, 9), new Vector3f(3, 5, 0), new Vector3f(7, 5, 3)},
            new Vector3f[] {new Vector3f(8, 2, 0), new Vector3f(5, 2, 8), new Vector3f(10, 2, 5), new Vector3f(7, 5, 8)},
            new Vector3f[] {new Vector3f(2, 3, 10), new Vector3f(10, 3, 5), new Vector3f(5, 3, 7)},
            new Vector3f[] {new Vector3f(9, 7, 5), new Vector3f(9, 8, 7), new Vector3f(11, 3, 2)},
            new Vector3f[] {new Vector3f(0, 2, 9), new Vector3f(9, 2, 7), new Vector3f(7, 2, 11), new Vector3f(9, 7, 5)},
            new Vector3f[] {new Vector3f(3, 2, 11), new Vector3f(8, 7, 0), new Vector3f(0, 7, 1), new Vector3f(1, 7, 5)},
            new Vector3f[] {new Vector3f(11, 1, 2), new Vector3f(7, 1, 11), new Vector3f(5, 1, 7)},
            new Vector3f[] {new Vector3f(3, 1, 11), new Vector3f(11, 1, 10), new Vector3f(8, 7, 9), new Vector3f(9, 7, 5)},
            new Vector3f[] {new Vector3f(11, 7, 0), new Vector3f(7, 5, 0), new Vector3f(5, 9, 0), new Vector3f(10, 11, 0), new Vector3f(1, 10, 0)},
            new Vector3f[] {new Vector3f(0, 5, 10), new Vector3f(0, 7, 5), new Vector3f(0, 8, 7), new Vector3f(0, 10, 11), new Vector3f(0, 11, 3)},
            new Vector3f[] {new Vector3f(10, 11, 5), new Vector3f(11, 7, 5)},
            new Vector3f[] {new Vector3f(5, 6, 10)},
            new Vector3f[] {new Vector3f(8, 0, 3), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(0, 9, 1), new Vector3f(5, 6, 10)},
            new Vector3f[] {new Vector3f(8, 1, 3), new Vector3f(8, 9, 1), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(1, 6, 2), new Vector3f(1, 5, 6)},
            new Vector3f[] {new Vector3f(6, 2, 5), new Vector3f(2, 1, 5), new Vector3f(8, 0, 3)},
            new Vector3f[] {new Vector3f(5, 6, 9), new Vector3f(9, 6, 0), new Vector3f(0, 6, 2)},
            new Vector3f[] {new Vector3f(5, 8, 9), new Vector3f(2, 8, 5), new Vector3f(3, 8, 2), new Vector3f(6, 2, 5)},
            new Vector3f[] {new Vector3f(3, 2, 11), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(0, 2, 8), new Vector3f(2, 11, 8), new Vector3f(5, 6, 10)},
            new Vector3f[] {new Vector3f(3, 2, 11), new Vector3f(0, 9, 1), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(5, 6, 10), new Vector3f(2, 9, 1), new Vector3f(11, 9, 2), new Vector3f(8, 9, 11)},
            new Vector3f[] {new Vector3f(11, 3, 6), new Vector3f(6, 3, 5), new Vector3f(5, 3, 1)},
            new Vector3f[] {new Vector3f(11, 8, 6), new Vector3f(6, 8, 1), new Vector3f(1, 8, 0), new Vector3f(6, 1, 5)},
            new Vector3f[] {new Vector3f(5, 0, 9), new Vector3f(6, 0, 5), new Vector3f(3, 0, 6), new Vector3f(11, 3, 6)},
            new Vector3f[] {new Vector3f(6, 9, 5), new Vector3f(11, 9, 6), new Vector3f(8, 9, 11)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(6, 10, 5)},
            new Vector3f[] {new Vector3f(3, 7, 0), new Vector3f(7, 4, 0), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(6, 10, 5), new Vector3f(9, 1, 0)},
            new Vector3f[] {new Vector3f(5, 6, 10), new Vector3f(9, 1, 4), new Vector3f(4, 1, 7), new Vector3f(7, 1, 3)},
            new Vector3f[] {new Vector3f(1, 6, 2), new Vector3f(1, 5, 6), new Vector3f(7, 4, 8)},
            new Vector3f[] {new Vector3f(6, 1, 5), new Vector3f(2, 1, 6), new Vector3f(0, 7, 4), new Vector3f(3, 7, 0)},
            new Vector3f[] {new Vector3f(4, 8, 7), new Vector3f(5, 6, 9), new Vector3f(9, 6, 0), new Vector3f(0, 6, 2)},
            new Vector3f[] {new Vector3f(2, 3, 9), new Vector3f(3, 7, 9), new Vector3f(7, 4, 9), new Vector3f(6, 2, 9), new Vector3f(5, 6, 9)},
            new Vector3f[] {new Vector3f(2, 11, 3), new Vector3f(7, 4, 8), new Vector3f(10, 5, 6)},
            new Vector3f[] {new Vector3f(6, 10, 5), new Vector3f(7, 4, 11), new Vector3f(11, 4, 2), new Vector3f(2, 4, 0)},
            new Vector3f[] {new Vector3f(1, 0, 9), new Vector3f(8, 7, 4), new Vector3f(3, 2, 11), new Vector3f(5, 6, 10)},
            new Vector3f[] {new Vector3f(1, 2, 9), new Vector3f(9, 2, 11), new Vector3f(9, 11, 4), new Vector3f(4, 11, 7), new Vector3f(5, 6, 10)},
            new Vector3f[] {new Vector3f(7, 4, 8), new Vector3f(11, 3, 6), new Vector3f(6, 3, 5), new Vector3f(5, 3, 1)},
            new Vector3f[] {new Vector3f(11, 0, 1), new Vector3f(11, 4, 0), new Vector3f(11, 7, 4), new Vector3f(11, 1, 5), new Vector3f(11, 5, 6)},
            new Vector3f[] {new Vector3f(6, 9, 5), new Vector3f(0, 9, 6), new Vector3f(11, 0, 6), new Vector3f(3, 0, 11), new Vector3f(4, 8, 7)},
            new Vector3f[] {new Vector3f(5, 6, 9), new Vector3f(9, 6, 11), new Vector3f(9, 11, 7), new Vector3f(9, 7, 4)},
            new Vector3f[] {new Vector3f(4, 10, 9), new Vector3f(4, 6, 10)},
            new Vector3f[] {new Vector3f(10, 4, 6), new Vector3f(10, 9, 4), new Vector3f(8, 0, 3)},
            new Vector3f[] {new Vector3f(1, 0, 10), new Vector3f(10, 0, 6), new Vector3f(6, 0, 4)},
            new Vector3f[] {new Vector3f(8, 1, 3), new Vector3f(6, 1, 8), new Vector3f(6, 10, 1), new Vector3f(4, 6, 8)},
            new Vector3f[] {new Vector3f(9, 2, 1), new Vector3f(4, 2, 9), new Vector3f(6, 2, 4)},
            new Vector3f[] {new Vector3f(3, 8, 0), new Vector3f(9, 2, 1), new Vector3f(4, 2, 9), new Vector3f(6, 2, 4)},
            new Vector3f[] {new Vector3f(0, 4, 2), new Vector3f(2, 4, 6)},
            new Vector3f[] {new Vector3f(8, 2, 3), new Vector3f(4, 2, 8), new Vector3f(6, 2, 4)},
            new Vector3f[] {new Vector3f(4, 10, 9), new Vector3f(4, 6, 10), new Vector3f(2, 11, 3)},
            new Vector3f[] {new Vector3f(11, 8, 2), new Vector3f(2, 8, 0), new Vector3f(6, 10, 4), new Vector3f(4, 10, 9)},
            new Vector3f[] {new Vector3f(2, 11, 3), new Vector3f(1, 0, 10), new Vector3f(10, 0, 6), new Vector3f(6, 0, 4)},
            new Vector3f[] {new Vector3f(8, 4, 1), new Vector3f(4, 6, 1), new Vector3f(6, 10, 1), new Vector3f(11, 8, 1), new Vector3f(2, 11, 1)},
            new Vector3f[] {new Vector3f(3, 1, 11), new Vector3f(11, 1, 4), new Vector3f(1, 9, 4), new Vector3f(11, 4, 6)},
            new Vector3f[] {new Vector3f(6, 11, 1), new Vector3f(11, 8, 1), new Vector3f(8, 0, 1), new Vector3f(4, 6, 1), new Vector3f(9, 4, 1)},
            new Vector3f[] {new Vector3f(3, 0, 11), new Vector3f(11, 0, 6), new Vector3f(6, 0, 4)},
            new Vector3f[] {new Vector3f(4, 11, 8), new Vector3f(4, 6, 11)},
            new Vector3f[] {new Vector3f(6, 8, 7), new Vector3f(10, 8, 6), new Vector3f(9, 8, 10)},
            new Vector3f[] {new Vector3f(3, 7, 0), new Vector3f(0, 7, 10), new Vector3f(7, 6, 10), new Vector3f(0, 10, 9)},
            new Vector3f[] {new Vector3f(1, 6, 10), new Vector3f(0, 6, 1), new Vector3f(7, 6, 0), new Vector3f(8, 7, 0)},
            new Vector3f[] {new Vector3f(10, 1, 6), new Vector3f(6, 1, 7), new Vector3f(7, 1, 3)},
            new Vector3f[] {new Vector3f(9, 8, 1), new Vector3f(1, 8, 6), new Vector3f(6, 8, 7), new Vector3f(1, 6, 2)},
            new Vector3f[] {new Vector3f(9, 7, 6), new Vector3f(9, 3, 7), new Vector3f(9, 0, 3), new Vector3f(9, 6, 2), new Vector3f(9, 2, 1)},
            new Vector3f[] {new Vector3f(7, 6, 8), new Vector3f(8, 6, 0), new Vector3f(0, 6, 2)},
            new Vector3f[] {new Vector3f(3, 6, 2), new Vector3f(3, 7, 6)},
            new Vector3f[] {new Vector3f(3, 2, 11), new Vector3f(6, 8, 7), new Vector3f(10, 8, 6), new Vector3f(9, 8, 10)},
            new Vector3f[] {new Vector3f(7, 9, 0), new Vector3f(7, 10, 9), new Vector3f(7, 6, 10), new Vector3f(7, 0, 2), new Vector3f(7, 2, 11)},
            new Vector3f[] {new Vector3f(0, 10, 1), new Vector3f(6, 10, 0), new Vector3f(8, 6, 0), new Vector3f(7, 6, 8), new Vector3f(2, 11, 3)},
            new Vector3f[] {new Vector3f(1, 6, 10), new Vector3f(7, 6, 1), new Vector3f(11, 7, 1), new Vector3f(2, 11, 1)},
            new Vector3f[] {new Vector3f(1, 9, 6), new Vector3f(9, 8, 6), new Vector3f(8, 7, 6), new Vector3f(3, 1, 6), new Vector3f(11, 3, 6)},
            new Vector3f[] {new Vector3f(9, 0, 1), new Vector3f(11, 7, 6)},
            new Vector3f[] {new Vector3f(0, 11, 3), new Vector3f(6, 11, 0), new Vector3f(7, 6, 0), new Vector3f(8, 7, 0)},
            new Vector3f[] {new Vector3f(7, 6, 11)},
            new Vector3f[] {new Vector3f(11, 6, 7)},
            new Vector3f[] {new Vector3f(3, 8, 0), new Vector3f(11, 6, 7)},
            new Vector3f[] {new Vector3f(1, 0, 9), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(1, 3, 9), new Vector3f(3, 8, 9), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(10, 2, 1), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(10, 2, 1), new Vector3f(3, 8, 0), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(9, 2, 0), new Vector3f(9, 10, 2), new Vector3f(11, 6, 7)},
            new Vector3f[] {new Vector3f(11, 6, 7), new Vector3f(3, 8, 2), new Vector3f(2, 8, 10), new Vector3f(10, 8, 9)},
            new Vector3f[] {new Vector3f(2, 6, 3), new Vector3f(6, 7, 3)},
            new Vector3f[] {new Vector3f(8, 6, 7), new Vector3f(0, 6, 8), new Vector3f(2, 6, 0)},
            new Vector3f[] {new Vector3f(7, 2, 6), new Vector3f(7, 3, 2), new Vector3f(1, 0, 9)},
            new Vector3f[] {new Vector3f(8, 9, 7), new Vector3f(7, 9, 2), new Vector3f(2, 9, 1), new Vector3f(7, 2, 6)},
            new Vector3f[] {new Vector3f(6, 1, 10), new Vector3f(7, 1, 6), new Vector3f(3, 1, 7)},
            new Vector3f[] {new Vector3f(8, 0, 7), new Vector3f(7, 0, 6), new Vector3f(6, 0, 1), new Vector3f(6, 1, 10)},
            new Vector3f[] {new Vector3f(7, 3, 6), new Vector3f(6, 3, 9), new Vector3f(3, 0, 9), new Vector3f(6, 9, 10)},
            new Vector3f[] {new Vector3f(7, 8, 6), new Vector3f(6, 8, 10), new Vector3f(10, 8, 9)},
            new Vector3f[] {new Vector3f(8, 11, 4), new Vector3f(11, 6, 4)},
            new Vector3f[] {new Vector3f(11, 0, 3), new Vector3f(6, 0, 11), new Vector3f(4, 0, 6)},
            new Vector3f[] {new Vector3f(6, 4, 11), new Vector3f(4, 8, 11), new Vector3f(1, 0, 9)},
            new Vector3f[] {new Vector3f(1, 3, 9), new Vector3f(9, 3, 6), new Vector3f(3, 11, 6), new Vector3f(9, 6, 4)},
            new Vector3f[] {new Vector3f(8, 11, 4), new Vector3f(11, 6, 4), new Vector3f(1, 10, 2)},
            new Vector3f[] {new Vector3f(1, 10, 2), new Vector3f(11, 0, 3), new Vector3f(6, 0, 11), new Vector3f(4, 0, 6)},
            new Vector3f[] {new Vector3f(2, 9, 10), new Vector3f(0, 9, 2), new Vector3f(4, 11, 6), new Vector3f(8, 11, 4)},
            new Vector3f[] {new Vector3f(3, 4, 9), new Vector3f(3, 6, 4), new Vector3f(3, 11, 6), new Vector3f(3, 9, 10), new Vector3f(3, 10, 2)},
            new Vector3f[] {new Vector3f(3, 2, 8), new Vector3f(8, 2, 4), new Vector3f(4, 2, 6)},
            new Vector3f[] {new Vector3f(2, 4, 0), new Vector3f(6, 4, 2)},
            new Vector3f[] {new Vector3f(0, 9, 1), new Vector3f(3, 2, 8), new Vector3f(8, 2, 4), new Vector3f(4, 2, 6)},
            new Vector3f[] {new Vector3f(1, 2, 9), new Vector3f(9, 2, 4), new Vector3f(4, 2, 6)},
            new Vector3f[] {new Vector3f(10, 3, 1), new Vector3f(4, 3, 10), new Vector3f(4, 8, 3), new Vector3f(6, 4, 10)},
            new Vector3f[] {new Vector3f(10, 0, 1), new Vector3f(6, 0, 10), new Vector3f(4, 0, 6)},
            new Vector3f[] {new Vector3f(3, 10, 6), new Vector3f(3, 9, 10), new Vector3f(3, 0, 9), new Vector3f(3, 6, 4), new Vector3f(3, 4, 8)},
            new Vector3f[] {new Vector3f(9, 10, 4), new Vector3f(10, 6, 4)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(7, 11, 6)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(7, 11, 6), new Vector3f(0, 3, 8)},
            new Vector3f[] {new Vector3f(0, 5, 1), new Vector3f(0, 4, 5), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(11, 6, 7), new Vector3f(4, 3, 8), new Vector3f(5, 3, 4), new Vector3f(1, 3, 5)},
            new Vector3f[] {new Vector3f(1, 10, 2), new Vector3f(9, 4, 5), new Vector3f(6, 7, 11)},
            new Vector3f[] {new Vector3f(8, 0, 3), new Vector3f(4, 5, 9), new Vector3f(10, 2, 1), new Vector3f(11, 6, 7)},
            new Vector3f[] {new Vector3f(7, 11, 6), new Vector3f(10, 4, 5), new Vector3f(2, 4, 10), new Vector3f(0, 4, 2)},
            new Vector3f[] {new Vector3f(8, 2, 3), new Vector3f(10, 2, 8), new Vector3f(4, 10, 8), new Vector3f(5, 10, 4), new Vector3f(11, 6, 7)},
            new Vector3f[] {new Vector3f(2, 6, 3), new Vector3f(6, 7, 3), new Vector3f(9, 4, 5)},
            new Vector3f[] {new Vector3f(5, 9, 4), new Vector3f(8, 6, 7), new Vector3f(0, 6, 8), new Vector3f(2, 6, 0)},
            new Vector3f[] {new Vector3f(7, 3, 6), new Vector3f(6, 3, 2), new Vector3f(4, 5, 0), new Vector3f(0, 5, 1)},
            new Vector3f[] {new Vector3f(8, 1, 2), new Vector3f(8, 5, 1), new Vector3f(8, 4, 5), new Vector3f(8, 2, 6), new Vector3f(8, 6, 7)},
            new Vector3f[] {new Vector3f(9, 4, 5), new Vector3f(6, 1, 10), new Vector3f(7, 1, 6), new Vector3f(3, 1, 7)},
            new Vector3f[] {new Vector3f(7, 8, 6), new Vector3f(6, 8, 0), new Vector3f(6, 0, 10), new Vector3f(10, 0, 1), new Vector3f(5, 9, 4)},
            new Vector3f[] {new Vector3f(3, 0, 10), new Vector3f(0, 4, 10), new Vector3f(4, 5, 10), new Vector3f(7, 3, 10), new Vector3f(6, 7, 10)},
            new Vector3f[] {new Vector3f(8, 6, 7), new Vector3f(10, 6, 8), new Vector3f(5, 10, 8), new Vector3f(4, 5, 8)},
            new Vector3f[] {new Vector3f(5, 9, 6), new Vector3f(6, 9, 11), new Vector3f(11, 9, 8)},
            new Vector3f[] {new Vector3f(11, 6, 3), new Vector3f(3, 6, 0), new Vector3f(0, 6, 5), new Vector3f(0, 5, 9)},
            new Vector3f[] {new Vector3f(8, 11, 0), new Vector3f(0, 11, 5), new Vector3f(5, 11, 6), new Vector3f(0, 5, 1)},
            new Vector3f[] {new Vector3f(6, 3, 11), new Vector3f(5, 3, 6), new Vector3f(1, 3, 5)},
            new Vector3f[] {new Vector3f(10, 2, 1), new Vector3f(5, 9, 6), new Vector3f(6, 9, 11), new Vector3f(11, 9, 8)},
            new Vector3f[] {new Vector3f(3, 11, 0), new Vector3f(0, 11, 6), new Vector3f(0, 6, 9), new Vector3f(9, 6, 5), new Vector3f(1, 10, 2)},
            new Vector3f[] {new Vector3f(0, 8, 5), new Vector3f(8, 11, 5), new Vector3f(11, 6, 5), new Vector3f(2, 0, 5), new Vector3f(10, 2, 5)},
            new Vector3f[] {new Vector3f(11, 6, 3), new Vector3f(3, 6, 5), new Vector3f(3, 5, 10), new Vector3f(3, 10, 2)},
            new Vector3f[] {new Vector3f(3, 9, 8), new Vector3f(6, 9, 3), new Vector3f(5, 9, 6), new Vector3f(2, 6, 3)},
            new Vector3f[] {new Vector3f(9, 6, 5), new Vector3f(0, 6, 9), new Vector3f(2, 6, 0)},
            new Vector3f[] {new Vector3f(6, 5, 8), new Vector3f(5, 1, 8), new Vector3f(1, 0, 8), new Vector3f(2, 6, 8), new Vector3f(3, 2, 8)},
            new Vector3f[] {new Vector3f(2, 6, 1), new Vector3f(6, 5, 1)},
            new Vector3f[] {new Vector3f(6, 8, 3), new Vector3f(6, 9, 8), new Vector3f(6, 5, 9), new Vector3f(6, 3, 1), new Vector3f(6, 1, 10)},
            new Vector3f[] {new Vector3f(1, 10, 0), new Vector3f(0, 10, 6), new Vector3f(0, 6, 5), new Vector3f(0, 5, 9)},
            new Vector3f[] {new Vector3f(3, 0, 8), new Vector3f(6, 5, 10)},
            new Vector3f[] {new Vector3f(10, 6, 5)},
            new Vector3f[] {new Vector3f(5, 11, 10), new Vector3f(5, 7, 11)},
            new Vector3f[] {new Vector3f(5, 11, 10), new Vector3f(5, 7, 11), new Vector3f(3, 8, 0)},
            new Vector3f[] {new Vector3f(11, 10, 7), new Vector3f(10, 5, 7), new Vector3f(0, 9, 1)},
            new Vector3f[] {new Vector3f(5, 7, 10), new Vector3f(10, 7, 11), new Vector3f(9, 1, 8), new Vector3f(8, 1, 3)},
            new Vector3f[] {new Vector3f(2, 1, 11), new Vector3f(11, 1, 7), new Vector3f(7, 1, 5)},
            new Vector3f[] {new Vector3f(3, 8, 0), new Vector3f(2, 1, 11), new Vector3f(11, 1, 7), new Vector3f(7, 1, 5)},
            new Vector3f[] {new Vector3f(2, 0, 11), new Vector3f(11, 0, 5), new Vector3f(5, 0, 9), new Vector3f(11, 5, 7)},
            new Vector3f[] {new Vector3f(2, 9, 5), new Vector3f(2, 8, 9), new Vector3f(2, 3, 8), new Vector3f(2, 5, 7), new Vector3f(2, 7, 11)},
            new Vector3f[] {new Vector3f(10, 3, 2), new Vector3f(5, 3, 10), new Vector3f(7, 3, 5)},
            new Vector3f[] {new Vector3f(10, 0, 2), new Vector3f(7, 0, 10), new Vector3f(8, 0, 7), new Vector3f(5, 7, 10)},
            new Vector3f[] {new Vector3f(0, 9, 1), new Vector3f(10, 3, 2), new Vector3f(5, 3, 10), new Vector3f(7, 3, 5)},
            new Vector3f[] {new Vector3f(7, 8, 2), new Vector3f(8, 9, 2), new Vector3f(9, 1, 2), new Vector3f(5, 7, 2), new Vector3f(10, 5, 2)},
            new Vector3f[] {new Vector3f(3, 1, 7), new Vector3f(7, 1, 5)},
            new Vector3f[] {new Vector3f(0, 7, 8), new Vector3f(1, 7, 0), new Vector3f(5, 7, 1)},
            new Vector3f[] {new Vector3f(9, 5, 0), new Vector3f(0, 5, 3), new Vector3f(3, 5, 7)},
            new Vector3f[] {new Vector3f(5, 7, 9), new Vector3f(7, 8, 9)},
            new Vector3f[] {new Vector3f(4, 10, 5), new Vector3f(8, 10, 4), new Vector3f(11, 10, 8)},
            new Vector3f[] {new Vector3f(3, 4, 0), new Vector3f(10, 4, 3), new Vector3f(10, 5, 4), new Vector3f(11, 10, 3)},
            new Vector3f[] {new Vector3f(1, 0, 9), new Vector3f(4, 10, 5), new Vector3f(8, 10, 4), new Vector3f(11, 10, 8)},
            new Vector3f[] {new Vector3f(4, 3, 11), new Vector3f(4, 1, 3), new Vector3f(4, 9, 1), new Vector3f(4, 11, 10), new Vector3f(4, 10, 5)},
            new Vector3f[] {new Vector3f(1, 5, 2), new Vector3f(2, 5, 8), new Vector3f(5, 4, 8), new Vector3f(2, 8, 11)},
            new Vector3f[] {new Vector3f(5, 4, 11), new Vector3f(4, 0, 11), new Vector3f(0, 3, 11), new Vector3f(1, 5, 11), new Vector3f(2, 1, 11)},
            new Vector3f[] {new Vector3f(5, 11, 2), new Vector3f(5, 8, 11), new Vector3f(5, 4, 8), new Vector3f(5, 2, 0), new Vector3f(5, 0, 9)},
            new Vector3f[] {new Vector3f(5, 4, 9), new Vector3f(2, 3, 11)},
            new Vector3f[] {new Vector3f(3, 4, 8), new Vector3f(2, 4, 3), new Vector3f(5, 4, 2), new Vector3f(10, 5, 2)},
            new Vector3f[] {new Vector3f(5, 4, 10), new Vector3f(10, 4, 2), new Vector3f(2, 4, 0)},
            new Vector3f[] {new Vector3f(2, 8, 3), new Vector3f(4, 8, 2), new Vector3f(10, 4, 2), new Vector3f(5, 4, 10), new Vector3f(0, 9, 1)},
            new Vector3f[] {new Vector3f(4, 10, 5), new Vector3f(2, 10, 4), new Vector3f(1, 2, 4), new Vector3f(9, 1, 4)},
            new Vector3f[] {new Vector3f(8, 3, 4), new Vector3f(4, 3, 5), new Vector3f(5, 3, 1)},
            new Vector3f[] {new Vector3f(1, 5, 0), new Vector3f(5, 4, 0)},
            new Vector3f[] {new Vector3f(5, 0, 9), new Vector3f(3, 0, 5), new Vector3f(8, 3, 5), new Vector3f(4, 8, 5)},
            new Vector3f[] {new Vector3f(5, 4, 9)},
            new Vector3f[] {new Vector3f(7, 11, 4), new Vector3f(4, 11, 9), new Vector3f(9, 11, 10)},
            new Vector3f[] {new Vector3f(8, 0, 3), new Vector3f(7, 11, 4), new Vector3f(4, 11, 9), new Vector3f(9, 11, 10)},
            new Vector3f[] {new Vector3f(0, 4, 1), new Vector3f(1, 4, 11), new Vector3f(4, 7, 11), new Vector3f(1, 11, 10)},
            new Vector3f[] {new Vector3f(10, 1, 4), new Vector3f(1, 3, 4), new Vector3f(3, 8, 4), new Vector3f(11, 10, 4), new Vector3f(7, 11, 4)},
            new Vector3f[] {new Vector3f(9, 4, 1), new Vector3f(1, 4, 2), new Vector3f(2, 4, 7), new Vector3f(2, 7, 11)},
            new Vector3f[] {new Vector3f(1, 9, 2), new Vector3f(2, 9, 4), new Vector3f(2, 4, 11), new Vector3f(11, 4, 7), new Vector3f(3, 8, 0)},
            new Vector3f[] {new Vector3f(11, 4, 7), new Vector3f(2, 4, 11), new Vector3f(0, 4, 2)},
            new Vector3f[] {new Vector3f(7, 11, 4), new Vector3f(4, 11, 2), new Vector3f(4, 2, 3), new Vector3f(4, 3, 8)},
            new Vector3f[] {new Vector3f(10, 9, 2), new Vector3f(2, 9, 7), new Vector3f(7, 9, 4), new Vector3f(2, 7, 3)},
            new Vector3f[] {new Vector3f(2, 10, 7), new Vector3f(10, 9, 7), new Vector3f(9, 4, 7), new Vector3f(0, 2, 7), new Vector3f(8, 0, 7)},
            new Vector3f[] {new Vector3f(10, 4, 7), new Vector3f(10, 0, 4), new Vector3f(10, 1, 0), new Vector3f(10, 7, 3), new Vector3f(10, 3, 2)},
            new Vector3f[] {new Vector3f(8, 4, 7), new Vector3f(10, 1, 2)},
            new Vector3f[] {new Vector3f(4, 1, 9), new Vector3f(7, 1, 4), new Vector3f(3, 1, 7)},
            new Vector3f[] {new Vector3f(8, 0, 7), new Vector3f(7, 0, 1), new Vector3f(7, 1, 9), new Vector3f(7, 9, 4)},
            new Vector3f[] {new Vector3f(0, 7, 3), new Vector3f(0, 4, 7)},
            new Vector3f[] {new Vector3f(8, 4, 7)},
            new Vector3f[] {new Vector3f(9, 8, 10), new Vector3f(10, 8, 11)},
            new Vector3f[] {new Vector3f(3, 11, 0), new Vector3f(0, 11, 9), new Vector3f(9, 11, 10)},
            new Vector3f[] {new Vector3f(0, 10, 1), new Vector3f(8, 10, 0), new Vector3f(11, 10, 8)},
            new Vector3f[] {new Vector3f(11, 10, 3), new Vector3f(10, 1, 3)},
            new Vector3f[] {new Vector3f(1, 9, 2), new Vector3f(2, 9, 11), new Vector3f(11, 9, 8)},
            new Vector3f[] {new Vector3f(9, 2, 1), new Vector3f(11, 2, 9), new Vector3f(3, 11, 9), new Vector3f(0, 3, 9)},
            new Vector3f[] {new Vector3f(8, 2, 0), new Vector3f(8, 11, 2)},
            new Vector3f[] {new Vector3f(11, 2, 3)},
            new Vector3f[] {new Vector3f(2, 8, 3), new Vector3f(10, 8, 2), new Vector3f(9, 8, 10)},
            new Vector3f[] {new Vector3f(0, 2, 9), new Vector3f(2, 10, 9)},
            new Vector3f[] {new Vector3f(3, 2, 8), new Vector3f(8, 2, 10), new Vector3f(8, 10, 1), new Vector3f(8, 1, 0)},
            new Vector3f[] {new Vector3f(1, 2, 10)},
            new Vector3f[] {new Vector3f(3, 1, 8), new Vector3f(1, 9, 8)},
            new Vector3f[] {new Vector3f(9, 0, 1)},
            new Vector3f[] {new Vector3f(3, 0, 8)},
            new Vector3f[] {}
    ));

    private TestShader shader;

    private Vao vao;

    private float surfaceLevel = 0.5f;

    public TestScreen() {
        if (instance == null)
            instance = this;
    }

    private final int CHUNK_SIZE = 32;
    private final float[][][] terrainValues = new float[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

    @Override
    public void init() {
        shader = new TestShader();

        Log.info("Hello3");

        Noise noise = new Noise(3, 70, 0.5f);
        float value = 0;
        for (int x = 0; x < 1000; x++)
            for (int y = 0; y < 1000; y++) {
                value = noise.calculateFractalNoise(x, y);
                if (value > 70 || value < -70)
                   Log.info(value);
            }

        Log.info("Hello2");

        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        for (int x = 0; x < CHUNK_SIZE; x++)
            for (int y = 0; y < CHUNK_SIZE; y++)
                for (int z = 0; z < CHUNK_SIZE; z++)
                    terrainValues[x][y][z] = 1f - Math.abs((new Vector3f(CHUNK_SIZE / 2f, CHUNK_SIZE / 2f, CHUNK_SIZE / 2f).distance(x, y, z)) / (CHUNK_SIZE / 2f));

        for (int x = 0; x < CHUNK_SIZE; x++)
            for (int y = 0; y < CHUNK_SIZE; y++)
                for (int z = 0; z < CHUNK_SIZE; z++)
                    marchingCubesSingleCell(x, y, z, vertices, indices);

        float[] vertex = new float[vertices.size() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            Vector3f vertice = vertices.get(i);
            for (int j = 0; j < 3; j++)
                vertex[i * 3 + j] = vertice.get(j);
        }

        vao = new Vao()
                .bind()
                .setVertexCount(vertices.size())
                .storeData(0, vertex, 3)
                .unbind();
    }

    public void marchingCubesSingleCell(int x, int y, int z, ArrayList<Vector3f> vertices, ArrayList<Integer> indices) {
        float[] f_eval = new float[8];
        int cubeCase = 0;
        for (int i = 0; i < 8; i++) {
            Vector3i vertexPos = VERTICES[i];
            Vector3i position = new Vector3i(x + vertexPos.x, y + vertexPos.y, z + vertexPos.z);
            if (position.x < CHUNK_SIZE && position.y < CHUNK_SIZE && position.z < CHUNK_SIZE)
                f_eval[i] = terrainValues[position.x][position.y][position.z];
            else
                f_eval[i] = 0;
            if (f_eval[i] < surfaceLevel)
                cubeCase += (int) Math.pow(2, i);
        }

        Vector3f[] faces = cases.get(cubeCase);

        for (Vector3f face : faces) {
            for (int i = 0; i < 3; i++)
                vertices.add(edgeToBoundary(x, y, z, f_eval, (int) face.get(i)));

        }

    }

    private Vector3f edgeToBoundary(int x, int y, int z, float[] f_eval, int edgeID) {

        Vector2f edge = EDGES[edgeID];
        Vector3f vert_pos0 = new Vector3f(VERTICES[(int) edge.x]);
        Vector3f vert_pos1 = new Vector3f(VERTICES[(int) edge.y]);
        float f0 = f_eval[(int) edge.x];
        float f1 = f_eval[(int) edge.y];
        float t0 = 1 - adapt(f0, f1);
        float t1 = 1 - t0;
        //return new Vector3f(x, y, z).add(new Vector3f(vert_pos0).mul(t0)).add(new Vector3f(vert_pos1).mul(t1));
        return new Vector3f(x + vert_pos0.x * t0 + vert_pos1.x * t1,
                y + vert_pos0.y * t0 + vert_pos1.y * t1,
                z + vert_pos0.z * t0 + vert_pos1.z * t1);

    }

    private float adapt(float v0, float v1) {
        //assert v1 > 0 != v0 > 0;
        if (v1 > surfaceLevel == v0 > surfaceLevel)
            return 0.5f;
        return (surfaceLevel - v0) / (v1 - v0);
        //return 0.5f;
    }

    @Override
    public void resize(int width, int height) {}

    private boolean wireframe = false;

    @Override
    public void update(float delta) {
        if (InputManager.instance.isKeyJustPressed(Keys.KEY_J))
            OpenGLUtils.wireframe(wireframe = !wireframe);
    }

    @Override
    public void render() {
        OpenGLUtils.disableCulling();
        shader.bind();
        shader.loadUniforms();
        vao.start();
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertexCount());
        vao.stop();
        shader.unbind();
        OpenGLUtils.enableCulling();
    }

    @Override
    public void delete() {}

}
