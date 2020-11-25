package de.undefinedhuman.core.light;

import de.undefinedhuman.core.manager.Manager;
import org.joml.Vector3f;

public class LightManager extends Manager {

    public static LightManager instance;

    private Light sun;

    public LightManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        super.init();
        this.sun = new Light(new Vector3f(10000, 10000, 10000), new Vector3f(1, 1, 1));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void delete() {
        super.delete();
    }

    public Light getSun() {
        return sun;
    }

}
