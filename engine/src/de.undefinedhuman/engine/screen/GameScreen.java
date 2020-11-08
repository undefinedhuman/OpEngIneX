package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.screen.Screen;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        BlueprintManager.instance.loadBlueprints(0);
        EntityManager.instance.addEntity(0, BlueprintManager.instance.getBlueprint(0).createInstance());

        Entity entity =  BlueprintManager.instance.getBlueprint(0).createInstance();
        entity.setPosition(0, 0, -10);
        EntityManager.instance.addEntity(1, entity);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void delete() {

    }

}
