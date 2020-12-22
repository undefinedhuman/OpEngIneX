package de.undefinedhuman.engine.config;

import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingType;
import de.undefinedhuman.engine.settings.SettingsList;
import de.undefinedhuman.engine.settings.types.BooleanSetting;

import java.util.ArrayList;

public class SettingsManager implements Manager {

    public static SettingsManager instance;
    public Setting displayHeight, displayWidth, fullScreen, language, vsync, samples, fps, closeKey;
    private SettingsList settings;

    public SettingsManager() {
        if (instance == null) instance = this;
        this.settings = new SettingsList();
    }

    @Override
    public void init() {
        settings.add(
                displayHeight = new Setting(SettingType.Int, "displayHeight", 1080),
                displayWidth = new Setting(SettingType.Int, "displayWidth", 1920),
                fullScreen = new BooleanSetting("fullScreen", false),
                language = new Setting(SettingType.String, "language", "eu_DE"),
                vsync = new BooleanSetting("vsync", false),
                samples = new Setting(SettingType.Int, "samples", 8),
                closeKey = new Setting(SettingType.Int, "closeKey", 256),
                fps = new Setting(SettingType.Int, "fps", 60));
    }

    @Override
    public void delete() {
        settings.delete();
    }

    public ArrayList<Setting> getSettings() {
        return settings.getSettings();
    }

}
