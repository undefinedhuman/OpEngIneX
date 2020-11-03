package de.undefinedhuman.core.manager;

import java.util.ArrayList;
import java.util.Arrays;

public class ManagerList {

    public ArrayList<Manager> managers;

    public ManagerList() {
        managers = new ArrayList<>();
    }

    public ManagerList addManager(Manager... managers) {
        this.managers.addAll(Arrays.asList(managers));
        return this;
    }

    public void init() {
        for (Manager manager : managers) manager.init();
    }

    public void resize(int width, int height) {
        for (Manager manager : managers) manager.resize(width, height);
    }

    public void update(float delta) {
        for (Manager manager : managers) manager.update(delta);
    }

    public void render() {
        for (Manager manager : managers) manager.render();
    }

    public void delete() {
        for (int i = managers.size() - 1; i >= 0; i--) managers.get(i).delete();
        managers.clear();
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

}
