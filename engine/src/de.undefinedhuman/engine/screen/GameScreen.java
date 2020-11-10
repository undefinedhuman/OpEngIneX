package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.screen.Screen;

import java.util.Random;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        BlueprintManager.instance.loadBlueprints(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        for(int i = 0; i < 1000; i++) {
            Entity entity = BlueprintManager.instance.getBlueprint(4).createInstance();
            entity.setPosition(new Random().nextInt(200) - 100, 0, new Random().nextInt(200) - 100);
            EntityManager.instance.addEntity(i, entity);
        }
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
