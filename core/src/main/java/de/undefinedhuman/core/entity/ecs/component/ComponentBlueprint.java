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

    private String componentName;

    public ComponentBlueprint(String componentName) {
        this.componentName = componentName;
    }

    public abstract Component createInstance(HashMap<ComponentType, ComponentParam> params);

    public void load(FsFile parentDir, SettingsObject settingsObject) {
        for(Setting setting : this.settings.getSettings())
            setting.loadSetting(parentDir, settingsObject);
    }

    public void save(FileWriter writer) {
        writer.writeString("{:" + componentName).nextLine();
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
