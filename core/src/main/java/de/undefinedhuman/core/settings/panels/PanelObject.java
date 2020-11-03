package de.undefinedhuman.core.settings.panels;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.utils.Tools;

import java.util.ArrayList;

public class PanelObject {

    public String key;

    protected ArrayList<Setting> settings = new ArrayList<>();

    public void save(FileWriter writer) {
        writer.writeString("{:" + getKey()).nextLine();
        Tools.saveSettings(writer, settings);
        writer.writeString("}").nextLine();
    }

    public PanelObject load(FsFile parentDir, SettingsObject settingsObject) {
        for(Setting setting : this.settings) setting.loadSetting(parentDir, settingsObject);
        return this;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public PanelObject setKey(String key) {
        this.key = key;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void delete() {
        for(Setting setting : settings)
            setting.delete();
        settings.clear();
    }

}
