package de.undefinedhuman.core.entity.ecs.blueprint;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.utils.Tools;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;

import java.util.Arrays;
import java.util.HashMap;

public class BlueprintManager extends Manager {

    public static BlueprintManager instance;

    private HashMap<Integer, Blueprint> blueprints;

    public BlueprintManager() {
        if (instance == null) instance = this;
        blueprints = new HashMap<>();
    }

    @Override
    public void init() {
        super.init();
        loadBlueprints(0, 1);
    }

    public boolean loadBlueprints(Integer... ids) {
        boolean loaded = false;
        for (int id : ids) {
            if (!hasBlueprint(id)) blueprints.put(id, loadBlueprint(new FsFile(Paths.ENTITY_PATH, id + "/settings.entity", false)));
            loaded |= hasBlueprint(id);
        }
        if (loaded)
            Log.info("Blueprint" + Tools.appendSToString(ids.length) + " loaded successfully: " + Arrays.toString(ids));
        return loaded;
    }

    public boolean hasBlueprint(int id) {
        return blueprints.containsKey(id);
    }

    @Override
    public void delete() {
        for (Blueprint blueprint : blueprints.values()) blueprint.delete();
        blueprints.clear();
    }

    public void addBlueprint(Blueprint blueprint) {
        this.blueprints.put(blueprint.getID(), blueprint);
    }

    public void removeBlueprints(int... ids) {
        for (int id : ids) {
            if (!hasBlueprint(id)) continue;
            blueprints.get(id).delete();
            blueprints.remove(id);
        }
    }

    public Blueprint getBlueprint(int id) {
        if (hasBlueprint(id) || loadBlueprints(id)) return blueprints.get(id);
        return hasBlueprint(0) ? getBlueprint(0) : null;
    }

    public static Blueprint loadBlueprint(FsFile file) {
        Blueprint blueprint = new Blueprint();
        FileReader reader = file.getFileReader(true);
        SettingsObject object = Tools.loadSettings(reader);

        for(Setting setting : blueprint.settings.getSettings())
            setting.loadSetting(reader.getParentDirectory(), object);

        for(ComponentType type : ComponentType.values()) {
            if(!object.containsKey(type.name())) continue;
            Object componentObject = object.get(type.name());
            if(!(componentObject instanceof SettingsObject)) continue;
            blueprint.addComponentBlueprint(type.load(reader.getParentDirectory(), (SettingsObject) object.get(type.name())));
        }

        reader.close();
        return blueprint;
    }

}