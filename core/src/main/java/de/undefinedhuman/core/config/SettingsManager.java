package de.undefinedhuman.core.config;

import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.settings.types.BooleanSetting;

import java.util.ArrayList;

public class SettingsManager extends Manager {

    public static SettingsManager instance;
    public Setting displayHeight, displayWidth, fullScreen, language, vsync, samples, fps, closeKey;
    ArrayList<Setting> settings;

    public SettingsManager() {
        if (instance == null) instance = this;
        this.settings = new ArrayList<>();
    }

    @Override
    public void init() {
        this.displayHeight = new Setting(SettingType.Int, "displayHeight", 720);
        this.displayWidth = new Setting(SettingType.Int, "displayWidth", 1280);
        this.fullScreen = new BooleanSetting("fullScreen", false);
        this.language = new Setting(SettingType.String, "language", "eu_DE");
        this.vsync = new BooleanSetting("vsync", false);
        this.samples = new Setting(SettingType.Int, "samples", 4);
        this.closeKey = new Setting(SettingType.Int, "closeKey", 256);
        this.fps = new Setting(SettingType.Int, "fps", 60);
    }

    @Override
    public void delete() {
        settings.clear();
    }

}
