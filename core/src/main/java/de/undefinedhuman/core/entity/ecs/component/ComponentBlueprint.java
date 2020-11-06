package de.undefinedhuman.core.entity.ecs.component;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingsList;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ComponentBlueprint {

    protected SettingsList settings = new SettingsList();

    public ComponentType type;

    public ComponentBlueprint() { }
    public ComponentBlueprint(ComponentType type) {
        this.type = type;
    }

    public abstract Component createInstance(HashMap<ComponentType, ComponentParam> params);

    public void load(FsFile parentDir, SettingsObject settingsObject) {
        for(Setting setting : this.settings.getSettings())
            setting.loadSetting(parentDir, settingsObject);
    }

    public void save(FileWriter writer) {
        writer.writeString("{:" + type.name()).nextLine();
        Tools.saveSettings(writer, settings.getSettings());
        writer.writeString("}").nextLine();
    }

    public ArrayList<Setting> getSettings() {
        return settings.getSettings();
    }

    // DON'T USE
    public ComponentBlueprint setType(ComponentType type) {
        this.type = type;
        return this;
    }

    public ComponentType getType() {
        return type;
    }

    public void delete() {
        settings.delete();
    }

}
