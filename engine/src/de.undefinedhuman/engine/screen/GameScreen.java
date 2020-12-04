package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.screen.Screen;
import de.undefinedhuman.core.utils.Tools;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.world.TerrainManager;
import de.undefinedhuman.core.world.TerrainTexture;
import de.undefinedhuman.core.world.generation.noise.Noise;
import de.undefinedhuman.core.world.shader.TerrainShader;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        BlueprintManager.instance.loadBlueprints(0, 1, 2, 3);

        TerrainManager.instance.setShader(new TerrainShader());
        final Noise noise = new Noise(6, 1f, 0.1f);
        TerrainManager.instance.addTerrain(TerrainTexture.GRASS, 0, 0, ((x, z) -> (noise.calculateFractalNoise(x, z) * 2f * 0.5f + 0.5f) * 70f - 35f));

        Camera.instance.setPosition(Variables.TERRAIN_SIZE/2f, 35f, Variables.TERRAIN_SIZE/2f);

        Noise treeNoise = new Noise(7, 6, 0.1f);
        for(int i = 0; i < 10000; i++) {
            int x = Tools.random.nextInt(800), z = Tools.random.nextInt(800);
            if(treeNoise.select(0.5f, treeNoise.calculateFractalNoise(x, z))) continue;
            Entity entity = BlueprintManager.instance.getBlueprint(Tools.random.nextInt(4)).createInstance();
            entity.setPosition(x, TerrainManager.instance.getHeightAtPosition(x, z) - 0.1f, z);
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
