package de.undefinedhuman.engine.entity.ecs.blueprint;

import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.engine.resources.ResourceManager;
import de.undefinedhuman.core.utils.Tools;

import java.util.Arrays;
import java.util.HashMap;

public class BlueprintManager implements Manager {

    public static BlueprintManager instance;

    private HashMap<Integer, Blueprint> blueprints;

    public BlueprintManager() {
        if (instance == null) instance = this;
        blueprints = new HashMap<>();
    }

    public boolean loadBlueprints(Integer... ids) {
        boolean loaded = false;
        for (int id : ids) {
            FsFile blueprintFile = new FsFile(Paths.ENTITY_PATH, id + "/settings.entity", false, false);
            if(blueprintFile.exists() && !hasBlueprint(id)) blueprints.put(id, ResourceManager.loadBlueprint(blueprintFile));
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

}
