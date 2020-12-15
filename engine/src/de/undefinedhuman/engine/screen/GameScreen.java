package de.undefinedhuman.engine.screen;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.ecs.blueprint.BlueprintManager;
import de.undefinedhuman.core.gui.GuiManager;
import de.undefinedhuman.core.gui.GuiShader;
import de.undefinedhuman.core.gui.GuiTransform;
import de.undefinedhuman.core.input.InputManager;
import de.undefinedhuman.core.input.Keys;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.light.PointLight;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.screen.Screen;
import de.undefinedhuman.core.shadows.ShadowManager;
import de.undefinedhuman.core.utils.Tools;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.water.WaterManager;
import de.undefinedhuman.core.water.WaterShader;
import de.undefinedhuman.core.world.TerrainManager;
import de.undefinedhuman.core.world.TerrainTexture;
import de.undefinedhuman.core.world.generation.noise.Noise;
import de.undefinedhuman.core.world.shader.TerrainShader;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class GameScreen implements Screen {

    public static GameScreen instance;

    public GameScreen() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        BlueprintManager.instance.loadBlueprints(0, 1, 2, 3, 4);
        TerrainManager.instance.setShader(new TerrainShader());
        WaterManager.instance.setShader(new WaterShader());
        GuiManager.instance.setShader(new GuiShader());

        GuiManager.instance.addGuiTransform(new GuiTransform(0.5f, 0.5f, 0.25f, 0.25f) {
            @Override
            public void bindTexture() {
                OpenGLUtils.bindTexture(0, ShadowManager.instance.getShadowMap());
            }

            @Override
            public void unbindTexture() {
            }
        });

        int tempMaxTerrain = 2;
        float cameraPos = Variables.TERRAIN_SIZE * tempMaxTerrain * .5f;
        Camera.instance.setPosition(cameraPos, 35f, cameraPos);

        for (int i = 0; i < tempMaxTerrain; i++)
            for (int j = 0; j < tempMaxTerrain; j++)
                generateTerrain(i, j);

        LightManager.instance.addPointLight(new PointLight(10f, new Vector3f(cameraPos, TerrainManager.instance.getHeightAtPosition(cameraPos, cameraPos) + 1, cameraPos), new Vector3f(0, 0, 1), new Vector3f(1f, 0.1f, 0.02f)));
    }

    final Noise terrainNoise = new Noise(5, 1f, 0.1f),
                treeNoise = new Noise(7, 6, 0.1f);

    private void generateTerrain(int terrainX, int terrainZ) {
        TerrainManager.instance.addTerrain(TerrainTexture.GRASS, terrainX, terrainZ, ((x, z) -> (terrainNoise.calculateFractalNoise(x, z) * 2f * 0.5f + 0.5f) * 70f - 35f));
        WaterManager.instance.addWater(terrainX, terrainZ);

        for (int i = 0; i < 2000; i++) {
            int x = (int) (Variables.TERRAIN_SIZE * terrainX + Tools.random.nextInt((int) Variables.TERRAIN_SIZE)), z = (int) (Variables.TERRAIN_SIZE * terrainZ + Tools.random.nextInt((int) Variables.TERRAIN_SIZE));
            float height = TerrainManager.instance.getHeightAtPosition(x, z) - 0.1f;
            if (treeNoise.select(0.5f, treeNoise.calculateFractalNoise(x, z)) || height <= 0.5f)
                continue;
            Entity entity = BlueprintManager.instance.getBlueprint(Tools.random.nextInt(4)).createInstance();
            entity.setScale(Tools.random.nextInt(2) + 3);
            entity.setPosition(x, height, z);
            EntityManager.instance.addEntity(i, entity);
        }
    }

    @Override
    public void resize(int width, int height) {}

    private boolean wireframe = false;

    @Override
    public void update(float delta) {
        if(InputManager.instance.isKeyJustPressed(Keys.KEY_J))
            OpenGLUtils.wireframe(wireframe = !wireframe);
    }

    @Override
    public void render() {
    }

    @Override
    public void delete() {}

}
