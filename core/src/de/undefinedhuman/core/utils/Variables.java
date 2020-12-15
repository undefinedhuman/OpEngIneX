package de.undefinedhuman.core.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class Variables {

    // Engine
    public static final String NAME = "OpEngIneX";
    public static final String VERSION = "0.5.6";

    public static final boolean DEBUG = false;
    public static final Color HITBOX_COLOR = new Color(0.41568628f, 0.3529412f, 0.8039216f, 0.4f);

    // Log
    public static final String LOG_MESSAGE_FORMAT = "[%prefix% - %time%] %message%";
    public static final String LOG_DATE_FORMAT = "dd-MM-yyyy - HH-mm-ss";

    // File System
    public static final String SEPARATOR = ";";
    public static final String FILE_SEPARATOR = "/";

    // Language System
    public static final String DEFAULT_LANGUAGE = "eu_DE";
    public static final String LANGUAGES_FILE_NAME = "languages.xml";

    // Window
    public static final float DELTA_MULTIPLIER = 1.0f;
    public static final float MAX_DELTA = 0.1f;
    public static final int NANOS_IN_SECOND = 1000 * 1000 * 1000;
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;

    // Camera
    public static final float FOV_ANGLE = 70f;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;
    public static final float FRUSTUM_LENGTH = FAR_PLANE - NEAR_PLANE;
    public static final float MOUSE_SENSITIVITY = 10f;
    public static final float CAMERA_MOVE_SPEED = 10f;
    public static final float VIEW_DISTANCE = FAR_PLANE;

    // Light
    public static final float AMBIENT_VALUE = 0.1f;

    // Entity
    public static final int DEFAULT_BLUEPRINT_ID = 0;

    // Terrain
    public static final float TERRAIN_SIZE = 800f;
    public static final int TERRAIN_VERTEX_COUNT = 256;
    public static final float TERRAIN_GRID_SIZE = TERRAIN_SIZE / (TERRAIN_VERTEX_COUNT - 1);

    // Wind
    public static final Vector2f WIND_DIRECTION = new Vector2f(1, 0.5f);
    public static final float WIND_FREQUENCY = 1f;
    public static final float WIND_GUSTS_DISTANCE = 0.25f;
    public static final float WIND_STRENGTH = 0.12f;

    // Fog
    public static final Vector3f FOG_COLOR = new Vector3f(1, 1, 1);
    public static final float FOG_DENSITY = 0.00175f;
    public static final float FOG_POWER = 7.5f;

    // Light
    public static final int MAX_POINT_LIGHTS = 5;

    // Water
    public static final float WATER_HEIGHT = 0;
    public static final float WATER_WAVE_MOVE_SPEED = 0.0276f;
    public static final float WATER_SHINE_DAMPER = 20;
    public static final float WATER_SPECULAR_STRENGTH = 0.56f;

    // Shadow
    public static final int SHADOW_MAP_SIZE = 4096;

}
