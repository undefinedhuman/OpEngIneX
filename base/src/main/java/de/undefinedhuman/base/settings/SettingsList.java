package de.undefinedhuman.base.settings;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsList {

    private ArrayList<Setting> settings = new ArrayList<>();

    public void addSettings(Setting... settings) {
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
