package de.undefinedhuman.core.utils;

import java.awt.*;

public class Variables {

    // Engine
    public static final String NAME = "OpEngIneX";
    public static final String VERSION = "0.2.0";

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

    // Display
    public static final float DELTA_MULTIPLIER = 1.0f;
    public static final float MAX_DELTA = 0.1f;
    public static final int NANOS_IN_SECOND = 1000 * 1000 * 1000;

    // Camera
    public static final float FOV_ANGLE = 70f;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000f;
    public static final float FRUSTUM_LENGTH = FAR_PLANE - NEAR_PLANE;
    public static final float MOUSE_SENSITIVITY = 10f;
    public static final float CAMERA_MOVE_SPEED = 10f;

    // Light
    public static final float AMBIENT_VALUE = 0.15f;

    // Entity
    public static final int DEFAULT_BLUEPRINT_ID = 0;

    // Terrain
    public static final float TERRAIN_SIZE = 800f;
    public static final int TERRAIN_VERTEX_COUNT = 128;
}
