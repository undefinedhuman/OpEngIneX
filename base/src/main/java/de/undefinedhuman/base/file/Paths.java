package de.undefinedhuman.base.file;

public enum Paths {

    ENGINE_PATH("../openginex/"), CONFIG_PATH(ENGINE_PATH.getPath() + "config/"), LOG_PATH(ENGINE_PATH.getPath() + "log/"), SCREENSHOT_PATH(ENGINE_PATH.getPath() + "screenshot/"),
    ENTITY_FOLDER("entity/"), SOUND_FOLDER("sounds/"), GUI_PATH("gui/"), LANGUAGE_PATH("language/");

    private String path;

    Paths(String path) { this.path = path; }
    public String getPath() { return path; }

}
