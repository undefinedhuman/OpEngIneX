package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.screen.Screen;
import de.undefinedhuman.core.world.TerrainManager;
import de.undefinedhuman.core.world.TerrainTexture;
import de.undefinedhuman.core.world.generation.noise.Noise;
import de.undefinedhuman.core.world.shader.TerrainShader;

import java.util.Random;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        BlueprintManager.instance.loadBlueprints(0, 1, 2, 3);

        for(int i = 0; i < 1000; i++) {
            Entity entity = BlueprintManager.instance.getBlueprint(new Random().nextInt(4)).createInstance();
            entity.setPosition(new Random().nextInt(200) - 100, 0, new Random().nextInt(200) - 100);
            EntityManager.instance.addEntity(i, entity);
        }

        TerrainManager.instance.setShader(new TerrainShader());
        Noise noise = new Noise(6, 1.7f, 0.1f);
        TerrainManager.instance.addTerrain(TerrainTexture.GRASS, 0, 0, (x, z) -> noise.fractal(x, z) * 100f - 50f);
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
