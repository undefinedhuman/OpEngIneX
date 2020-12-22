package de.undefinedhuman.engine.entity.ecs.component;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingsList;
import de.undefinedhuman.engine.settings.SettingsObject;
import de.undefinedhuman.core.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ComponentBlueprint {

    protected SettingsList settings = new SettingsList();
    public ComponentType type;

    public ComponentBlueprint() {}

    public abstract Component createInstance(HashMap<ComponentType, ComponentParam> params);

    public void load(FsFile parentDir, SettingsObject settingsObject) {
        for(Setting setting : settings.getSettings()) {
            setting.loadSetting(parentDir, settingsObject);
        }
    }

    public void save(FileWriter writer) {
        writer.writeString("{:" + type.name()).nextLine();
        Tools.saveSettings(writer, settings.getSettings());
        writer.writeString("}").nextLine();
    }

    public ArrayList<Setting> getSettings() {
        return settings.getSettings();
    }

    public void delete() {
        settings.delete();
    }

}
