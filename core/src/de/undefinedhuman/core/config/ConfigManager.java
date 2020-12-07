package de.undefinedhuman.core.config;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.log.Log;

public class ConfigManager implements Manager {

    public static ConfigManager instance;
    private FsFile file;

    public ConfigManager() {
        if (instance == null) instance = this;
    }

    @Override
    public void init() {
        String fileName = "config.txt";
        file = new FsFile(Paths.CONFIG_PATH, fileName, false);
        if (!file.isEmpty()) load();
    }

    @Override
    public void delete() {
        save();
    }

    public void save() {
        FileWriter writer = file.getFileWriter(true);
        try {
            for (Setting setting : SettingsManager.instance.getSettings()) writer.writeString(setting.getKey()).writeValue(setting.getValue()).nextLine();
            writer.close();
            Log.info("Config file was saved successfully!");
        } catch (Exception ex) {
            Log.error("Error while saving the config \n" + ex.getMessage());
        }
    }

    public void load() {
        try {
            FileReader reader = file.getFileReader(true);
            while (reader.nextLine() != null) {
                String key = reader.getNextString(), value = reader.getNextString();
                for (Setting setting : SettingsManager.instance.getSettings())
                    if (setting.getKey().equalsIgnoreCase(key)) setting.setValue(value);
            }
            reader.close();
            Log.info("Config file was loaded successfully!");
        } catch (Exception ex) {
            Log.error("Error while loading the config file. \n" + ex.getMessage());
        }
    }

}
