package de.undefinedhuman.engine.settings;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsList {

    private ArrayList<Setting> settings = new ArrayList<>();

    public void add(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void delete() {
        for(Setting setting : settings) setting.delete();
        settings.clear();
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

}
